
package mta.se.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import mta.se.controllers.SpaceInvaders;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;


public class GameOver extends InvadersScreen {
	/** the SpriteBatch used to draw the background, logo and text **/
	private final SpriteBatch spriteBatch;
	/** the background texture **/
	private final Texture background;
	/** the logo texture **/
	private final Texture logo;
	/** the font **/
	private final BitmapFont font;
	/** is done flag **/
	private boolean isDone = false;
	/** view & transform matrix **/
	private final Matrix4 viewMatrix = new Matrix4();
	private final Matrix4 transformMatrix = new Matrix4();
    private String usernameText;
    private int scoreText;
    public String[] usernames = new String[5];
    public String[] scores = new String[5];

	public GameOver(SpaceInvaders invaders) throws TransformerException, ParserConfigurationException, IOException, SAXException {
		super(invaders);
		spriteBatch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("data/planet.jpg"));
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		logo = new Texture(Gdx.files.internal("data/logo.png"));
		logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		font = new BitmapFont(Gdx.files.internal("data/font16.fnt"), Gdx.files.internal("data/font16.png"), false);

		if (invaders.getController() != null) {
			invaders.getController().addListener(new ControllerAdapter() {
				@Override
				public boolean buttonUp(Controller controller,
						int buttonIndex) {
					controller.removeListener(this);
					isDone = true;
					return false;
				}
			});
		}

        usernameText = MainMenuScreen.getUsername();
        scoreText = GameScreen.getScore();
        GameScreen.setScore(0);

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.parse("scores.xml");
        Element rootElement = doc.getDocumentElement();

        NodeList list = doc.getElementsByTagName("player");

        for(int c=4; c < list.getLength(); c++) {
            Node node = list.item(4);
            node.getParentNode().removeChild(node);
        }

        Element player = doc.createElement("player");
        list.item(0).getParentNode().insertBefore(player, list.item(0));

        Element username = doc.createElement("username");
        username.appendChild(doc.createTextNode(usernameText));
        player.appendChild(username);

        Element score = doc.createElement("score");
        score.appendChild(doc.createTextNode(Integer.toString(scoreText)));
        player.appendChild(score);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("scores.xml"));
        transformer.transform(source, result);


        for(int c=0; c < list.getLength(); c++) {
            Node info = list.item(c);
            if(info.getNodeType() == Node.ELEMENT_NODE) {
                Element details = (Element) info;
                usernames[c] = (String)details.getElementsByTagName("username").item(0).getTextContent();
                scores[c] = (String)details.getElementsByTagName("score").item(0).getTextContent();
            }
        }

        for(int c=0; c < scores.length-1; c++)
        {
            for(int c1=c+1; c1 < scores.length; c1++)
            {
                if(Integer.parseInt(scores[c]) > Integer.parseInt(scores[c1]))
                {
                   System.out.println("intershimbare");
                   String aux = scores[c];
                   scores[c]=scores[c1];
                    scores[c1]=aux;
                    aux=usernames[c];
                    usernames[c]=usernames[c1];
                    usernames[c1]=aux;
                }

            }
        }

	}

	@Override
	public void dispose () {
		spriteBatch.dispose();
		background.dispose();
		logo.dispose();
		font.dispose();
	}

	@Override
	public boolean isDone () {
		return isDone;
	}

	@Override
	public void draw (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		viewMatrix.setToOrtho2D(0, 0, 480, 320);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.setTransformMatrix(transformMatrix);
		spriteBatch.begin();
		spriteBatch.disableBlending();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(background, 0, 0, 480, 320, 0, 0, 512, 512, false, false);
		spriteBatch.enableBlending();
		spriteBatch.draw(logo, 0, 320 - 128, 480, 128, 0, 256, 512, 256, false, false);
		String text = "It is the end my friend.\nTouch to continue!";
		TextBounds bounds = font.getMultiLineBounds(text);
		spriteBatch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
		font.drawMultiLine(spriteBatch, text, 0, 160 + bounds.height / 2, 480, HAlignment.CENTER);


        for(int i=0; i < usernames.length; i++)
        {
            String score = usernames[i] + " - " + scores[i];
            font.draw(spriteBatch, score, 150, 50 + i * 15);
        }
		spriteBatch.end();
	}

	@Override
	public void update (float delta) {
		if (Gdx.input.justTouched()) {
			isDone = true;
		}
	}
}
