package fr.lasschko.pocketimperium.pocketimperium.controller;

import fr.lasschko.pocketimperium.pocketimperium.model.*;
import fr.lasschko.pocketimperium.pocketimperium.view.HexView;
import fr.lasschko.pocketimperium.pocketimperium.view.SectorView;
import fr.lasschko.pocketimperium.pocketimperium.view.ShipView;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Contrôleur pour le plateau de jeu de Pocket Imperium. Cette classe gère
 * l'ensemble des interactions graphiques du plateau de jeu, allant de sa
 * génération, jusqu'à la gestion des différents panels d'information.
 *
 * <p>
 * Elle implémente l'interface {@link javafx.fxml.Initializable}, ce qui lui permet
 * d'initialiser les différents composants JavaFX après le chargement du fichier FXML.
 * </p>
 *
 * @author Paul et Yevhenii
 * @version 1.0
 */
public class GameBoardController implements Initializable {
    private final List<HexView> hexViews = new ArrayList<>();
    private final List<SectorView> sectorViews = new ArrayList<>();
    private Game game;
    private GameManager gameManager;
    private final List<ShipView> shipViews = new ArrayList<>();

    /**
     * Rectangle qui permet de faire une transition fondue lors du lancement de la partie.
     */
    @FXML
    private Rectangle fadeRectangle;

    /**
     * Les éléments de texte qui permettent d'afficher le tour actuel, la phase, et les
     * commandes définies par chaque joueur.
     */
    @FXML
    private Text roundText;
    @FXML
    private Text phaseText;
    @FXML
    private Text commandsReveal;

    /**
     * Surface consacrée au plateau de jeu dans le fichier fxml. Il était impossible de le
     * générer avec les formes proposées de base par JavaFX. Donc, on déclare l'emplacement,
     * et on génère le terrain depuis ce fichier.
     */
    @FXML
    private Pane rootLayout;

    /**
     * Couleurs des joueurs dans le panneau de score.
     */
    @FXML
    private Rectangle playerSColor1;
    @FXML
    private Rectangle playerSColor2;
    @FXML
    private Rectangle playerSColor3;

    /**
     * Pseudos des joueurs dans le panneau de score.
     */
    @FXML
    private Text playerSName1;
    @FXML
    private Text playerSName2;
    @FXML
    private Text playerSName3;

    /**
     * Nombre de vaisseaux pour chaque joueur dans le panneau de score.
     */
    @FXML
    private Text playerSShip1;
    @FXML
    private Text playerSShip2;
    @FXML
    private Text playerSShip3;

    /**
     * Score des joueurs dans le panneau de score.
     */
    @FXML
    private Text playerSScore1;
    @FXML
    private Text playerSScore2;
    @FXML
    private Text playerSScore3;

    @FXML
    private StackPane pauseMenu;

    /**
     * Initialise le contrôleur après le chargement du fichier FXML.
     * Ici, on gère simplement la transition au fondu avec le {@link #fadeRectangle}.
     *
     * @param url L'emplacement utilisé pour résoudre les chemins relatifs.
     * @param resourceBundle Les ressources de localisation pour cette classe.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), fadeRectangle);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0);
        fadeOut.play();
    }

    /**
     * Initialise les données du contrôleur avec l'instance de {@link Game} passée
     * en paramètre. Crée le plateau d'hexagones, met à jour l'UI, puis démarre
     * la logique de jeu via {@link GameManager}.
     *
     * @param game L'instance du jeu contenant les informations nécessaires
     *             (joueurs, secteurs, etc.)
     */
    public void initData(Game game) {
        this.game = game;
        this.gameManager = new GameManager(this.game, this);

        updateUiContent();

        createHexBoard();
        gameManager.start();
    }

    /**
     * Défini / redéfini le panneau des scores dédié aux informations des joueurs avec
     * les informations de la partie : noms, couleurs, nombre de vaisseaux, scores.
     */
    public void updateUiContent() {
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Player player3 = game.getPlayers().get(2);

        playerSName1.setText(player1.getName());
        playerSName2.setText(player2.getName());
        playerSName3.setText(player3.getName());

        playerSColor1.setFill(player1.getColor());
        playerSColor2.setFill(player2.getColor());
        playerSColor3.setFill(player3.getColor());

        playerSShip1.setText(String.valueOf(player1.getShips().size()));
        playerSShip2.setText(String.valueOf(player2.getShips().size()));
        playerSShip3.setText(String.valueOf(player3.getShips().size()));

        playerSScore1.setText(String.valueOf(player1.getScore()));
        playerSScore2.setText(String.valueOf(player2.getScore()));
        playerSScore3.setText(String.valueOf(player3.getScore()));
    }

    /**
     * Met à jour l'affichage du numéro de round dans le panneau des informations de la partie.
     *
     * @param round Le numéro du round actuel (0-based), qui sera incrémenté de 1
     *              pour l'affichage utilisateur.
     */
    public void updateRoundText(int round) {
        round++;
        roundText.setText("Round - " + round);
    }

    /**
     * Met à jour l'affichage de la phase de jeu dans le panneau des informations de la partie.
     *
     * @param phase L'identifiant de la phase actuelle.
     */
    public void updatePhaseText(int phase) {
        phaseText.setText("Phase - " + phase);
    }

    /**
     * Met à jour le texte affichant les commandes révélées par chaque joueur
     * lors d'un tour.
     *
     * @param tourIndex L'index du tour pour lequel afficher les commandes.
     * @param players La liste de joueurs dont on affiche les commandes.
     */
    public void updateCommandsRevealText(int tourIndex, List<Player> players) {
        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("Tour - ").append(tourIndex + 1).append("\n");

        for (Player player : players) {
            textBuilder.append(player.getName())
                    .append(": ")
                    .append(player.getCommandOrder().get(tourIndex))
                    .append("\n");
        }

        commandsReveal.setText(textBuilder.toString());
    }

    /**
     * Affiche le menu de pause en rendant visible et en augmentant l'opacité
     * de {@link #pauseMenu}.
     */
    @FXML
    public void openPauseMenu() {
        pauseMenu.setOpacity(1);
        pauseMenu.setVisible(true);
    }

    /**
     * Masque le menu de pause en réduisant son opacité et en le rendant invisible.
     */
    @FXML
    public void closePauseMenu() {
        pauseMenu.setOpacity(0);
        pauseMenu.setVisible(false);
    }

    /**
     * Retourne la liste des {@link HexView} composant le plateau de jeu.
     *
     * @return La liste de tous les hexagones graphiques sur le plateau.
     */
    public List<HexView> getHexViews(){
        return hexViews;
    }

    /**
     * Retourne le {@link Pane} racine dans lequel sont ajoutés tous les éléments
     * graphiques du plateau.
     *
     * @return Le {@link Pane} racine de la scène
     */
    public Pane getRootLayout() {
        return rootLayout;
    }

    /**
     * Crée l'ensemble des hexagones pour chaque secteur défini dans la classe
     * {@link Game}, puis les ajoute à la racine graphique {@link #rootLayout}.
     */
    private void createHexBoard() {
        for (Sector sector : game.getSectors()) {
            addSector(sector);
        }
        for (HexView hexView : hexViews) {
            rootLayout.getChildren().add(hexView.getPane());
        }
    }

    /**
     * Crée et ajoute à la vue un nouveau secteur à partir d'un objet {@link Sector}.
     * Les hexagones de ce secteur sont également créés et ajoutés
     * dans la liste des {@link #hexViews}.
     *
     * @param sector L'objet métier décrivant un secteur du jeu
     */
    private void addSector(Sector sector) {
        SectorView sectorView = new SectorView(sector);
        sectorViews.add(sectorView);
        hexViews.addAll(sectorView.addHexes());
    }

    /**
     * Ajoute la vue d'un vaisseau à la liste des {@link #shipViews}
     * et met à jour l'interface utilisateur pour afficher le nouveau nombre
     * de vaisseaux possédé par le joueur.
     *
     * @param shipView La vue graphique représentant un vaisseau
     */
    public void addShipView(ShipView shipView) {
        shipViews.add(shipView);

        updateUiContent();
    }

    /**
     * Retourne la liste des vaisseaux actuellement affichés dans le jeu.
     *
     * @return La liste des objets {@link ShipView}
     */
    public List<ShipView> getShipViews(){
        return shipViews;
    }
}
