package fr.lasschko.pocketimperium.pocketimperium.model;

import fr.lasschko.pocketimperium.pocketimperium.controller.CommandSelectionController;
import fr.lasschko.pocketimperium.pocketimperium.controller.GameBoardController;
import fr.lasschko.pocketimperium.pocketimperium.utils.ErrorPopup;
import fr.lasschko.pocketimperium.pocketimperium.view.HexView;
import fr.lasschko.pocketimperium.pocketimperium.view.ShipView;
import javafx.application.Platform;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Classe gérant la logique du jeu, les phases, les actions des joueurs et la communication avec l'interface utilisateur.
 * Elle orchestre les différentes phases du jeu et permet aux joueurs d'effectuer des actions comme l'expansion, l'exploration, etc.
 */
public class GameManager {
    private final PhaseManager phaseManager;
    private final Game game;
    private final AtomicBoolean reverse = new AtomicBoolean(false);
    private final AtomicBoolean allPlayersReady = new AtomicBoolean(false);
    private final GameBoardController gameBoardController;
    private final CommandSelectionController commandSelectionController;
    private Map<Player, List<String>> playerCommands;
    private ShipView selectedShipView;
    private int round;

    /**
     * Constructeur pour initialiser le gestionnaire de jeu.
     *
     * @param game                Le jeu associé.
     * @param gameBoardController Le contrôleur de la carte du jeu.
     */
    public GameManager(Game game, GameBoardController gameBoardController) {
        this.phaseManager = new PhaseManager(game.getPhase(), game.getTurn());
        this.game = game;
        this.gameBoardController = gameBoardController;
        this.commandSelectionController = new CommandSelectionController(game);

        this.round = game.getRound();
        this.playerCommands = new LinkedHashMap<>();
    }

    /**
     * Récupère la phase actuelle du jeu.
     *
     * @return Le numéro de la phase actuelle.
     */
    public int getPhase() {
        gameBoardController.updatePhaseText(phaseManager.getPhase());
        return phaseManager.getPhase();
    }

    /**
     * Passe à la phase suivante.
     */
    public void nextPhase() {
        phaseManager.nextPhase();
        game.setPhase(phaseManager.getPhase());
    }

    /**
     * Récupère le numéro de la manche actuelle.
     *
     * @return Le numéro de la manche actuelle.
     */
    public int getRound() {
        return round;
    }

    /**
     * Définit le numéro de la manche actuelle.
     *
     * @param round Le numéro de la nouvelle manche.
     */
    public void setRound(int round) {
        this.round = round;
        gameBoardController.updateRoundText(round);
        game.setRound(this.round);
    }

    /**
     * Incrémente le numéro de la manche actuelle de 1.
     */
    public void addRound() {
        setRound(getRound() + 1);
    }

    /**
     * Démarre le jeu en lançant les différentes phases dans un thread séparé.
     * La boucle continue jusqu'à la fin des 9 manches.
     */
    public void start() {
        new Thread(() -> {
            while (this.getRound() < 9) {
                switch (this.getPhase()) {
                    case 0:
                        Platform.runLater(this::startPhase0);
                        break;
                    case 1:
                        startPhase1();
                        break;
                    case 2:
                        startPhase2();
                        break;
                    case 3:
                        startPhase3();
                        break;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            Player winner = getWinner(game.getPlayers());
        }).start();
    }

    /**
     * Détermine le joueur gagnant en fonction du score.
     *
     * @param players Liste des joueurs.
     * @return Le joueur avec le score le plus élevé.
     */
    public Player getWinner(List<Player> players) {
        Player winner = null;
        int highestScore = Integer.MIN_VALUE;

        for (Player player : players) {
            int playerScore = player.getScore();
            if (playerScore > highestScore) {
                highestScore = playerScore;
                winner = player;
            }
        }

        return winner;
    }

    /**
     * Démarre la phase 0 du jeu, permettant aux joueurs d'expansionner leurs systèmes de niveau 1.
     */
    public void startPhase0() {
        for (HexView hexView : gameBoardController.getHexViews()) {
            Hex hex = hexView.getHex();
            hexView.getPolygon().setOnMouseClicked(event -> {
                Player currentPlayer = game.getCurrentPlayer();
                if (hex.getSystemLevel() == SystemLevel.LEVEL_1 && !hex.getSector().isInitialDeployed()) {
                    for (int i = 0; i < 2; i++) {
                        new ExpandCommand().execute(gameBoardController, currentPlayer, hexView);
                    }

                    if (!reverse.get()) {
                        game.changeCurrentPlayerIndex(1);
                        if (game.getCurrentPlayerIndex() == 3) {
                            reverse.set(true);
                            game.changeCurrentPlayerIndex(-1);
                        }
                    } else {
                        game.changeCurrentPlayerIndex(-1);
                        if (game.getCurrentPlayerIndex() == -1) {
                            game.changeCurrentPlayerIndex(1);
                            this.nextPhase();
                        }
                    }

                } else {
                    ErrorPopup.showError("Ce n'est pas une planète avec un niveau de système 1 ou ce secteur est déjà déployé.");
                }
            });
        }
    }

    /**
     * Démarre la phase 1 du jeu, où chaque joueur choisit ses commandes.
     */
    public void startPhase1() {
        playerCommands = new LinkedHashMap<>();
        for (Player player : game.getPlayers()) {
            player.setCommandOrder(new ArrayList<>());
        }

        for (int i = 0; i < game.getNumberOfPlayers(); i++) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> commandSelectionController.createCommandSelection(latch));
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            playerCommands.put(game.getCurrentPlayer(), game.getCurrentPlayer().getCommandOrder());
            game.changeCurrentPlayerIndex(1);
        }

        allPlayersReady.set(true);
        game.setCurrentPlayerIndex(0);
        nextPhase();
    }

    /**
     * Démarre la phase 2 du jeu, où les joueurs exécutent leurs commandes.
     */
    private void startPhase2() {
        List<List<Player>> order = sortOrderOfExecution();
        System.out.println("Phase 2 commencée");

        for (int i = 0; i < order.size(); i++) {
            int commandIndex = i;
            List<Player> currentPlayers = order.get(i);
            gameBoardController.updateCommandsRevealText(i, currentPlayers);

            for (Player player : currentPlayers) {
                CountDownLatch latch = new CountDownLatch(1);

                int finalI = i;
                Platform.runLater(() -> {
                    String command = playerCommands.get(player).get(commandIndex);
                    System.out.println("Le joueur " + player.getName() + " exécute la commande: " + command);

                    switch (command) {
                        case "Expand":
                            executeExpandCommand(player, finalI, latch);
                            break;
                        case "Explore":
                            executeExploreCommand(player, finalI, latch);
                            break;
                        case "Exterminate":
                            System.out.println("Exterminer");
                            latch.countDown();
                            break;
                        default:
                            System.err.println("Commande inconnue: " + command);
                            latch.countDown();
                    }
                });

                try {
                    latch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                game.setCurrentPlayerIndex(0);
            }
        }

        System.out.println("Phase 2 terminée");
        nextPhase();
    }

    /**
     * Démarre la phase 3 du jeu, où les joueurs choisissent un secteur et comptabilisent leur score.
     */
    public void startPhase3() {
        this.sustainShips();
        gameBoardController.updateUiContent();

        for (Player player : game.getPlayers()) {
            CountDownLatch latch = new CountDownLatch(1);

            chooseSector(player, latch);

            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Le joueur : " + player.getName() + " a marqué " + player.getScore());
            gameBoardController.updateUiContent();
        }

        for (Sector sector : game.getSectors()) {
            sector.resetIsScored();
        }
        nextPhase();
        addRound();
    }

    /**
     * Exécute la commande "Expand", qui permet au joueur d'ajouter de nouveaux vaisseaux sur les hexes qu'il possède.
     * Une fois tous les vaisseaux ajoutés, le verrouillage de phase est décompté.
     *
     * @param player Le joueur qui exécute la commande.
     * @param orderIndex L'index de l'ordre actuel.
     * @param phaseLatch Le signal qui confirme la fin de la phase.
     */
    private void executeExpandCommand(Player player, int orderIndex, CountDownLatch phaseLatch) {
        int amount = getCommandEffectiveness("Expand", orderIndex);
        CountDownLatch expandLatch = new CountDownLatch(amount);

        for (HexView hexView : gameBoardController.getHexViews()) {
            Hex hex = hexView.getHex();
            hexView.getPolygon().setOnMouseClicked(event -> {
                if (hex.isControlledBy(player) && expandLatch.getCount() > 0) {
                    new ExpandCommand().execute(gameBoardController, player, hexView);
                    expandLatch.countDown();

                    if (expandLatch.getCount() == 0) {
                        phaseLatch.countDown();
                    }
                } else {
                    ErrorPopup.showError("You don't control this planet");
                }
            });
        }
    }

    /**
     * Exécute la commande "Explore", qui permet au joueur de déplacer ses vaisseaux sur de nouveaux hexes.
     * Chaque mouvement est vérifié, et le compte des vaisseaux est mis à jour.
     *
     * @param player Le joueur qui exécute la commande.
     * @param orderIndex L'index de l'ordre actuel.
     * @param latch Le signal qui confirme la fin de la phase.
     */
    private void executeExploreCommand(Player player, int orderIndex, CountDownLatch latch) {
        int amount = getCommandEffectiveness("Explore", orderIndex);
        System.out.println(amount);
        Map<ShipView, Integer> moveCounts = new HashMap<>();
        AtomicInteger shipsMoved = new AtomicInteger(0);
        AtomicBoolean isCurrentShipMoving = new AtomicBoolean(false);

        selectShipView(player);

        for (HexView hexView : gameBoardController.getHexViews()) {
            hexView.getPolygon().setOnMouseClicked(event -> {
                if (selectedShipView != null && shipsMoved.get() < amount) {
                    if (isCurrentShipMoving.get() && !moveCounts.containsKey(selectedShipView)) {
                        ErrorPopup.showError("You must finish moving the current ship before selecting another.");
                        return;
                    }

                    isCurrentShipMoving.set(true);
                    moveCounts.putIfAbsent(selectedShipView, 0);
                    int currentMoveCount = moveCounts.get(selectedShipView);
                    int moveDistance = game.getHexesGraph().getMoveCount(selectedShipView.getHex(), hexView.getHex());

                    if (hexView.getHex().isControlledBy() && !hexView.getHex().getControlledBy().equals(player)) {
                        ErrorPopup.showError("You cannot move to a hex controlled by another player.");
                        return;
                    }

                    if (moveDistance >= 0 && moveDistance <= 2 && currentMoveCount + moveDistance <= 2) {
                        new ExploreCommand(game).execute(selectedShipView, hexView);

                        currentMoveCount += moveDistance;
                        moveCounts.put(selectedShipView, currentMoveCount);

                        if (currentMoveCount == 2) {
                            shipsMoved.incrementAndGet();
                            isCurrentShipMoving.set(false);
                            selectedShipView.deselect();
                        }

                        if (shipsMoved.get() == amount) {
                            latch.countDown();
                        }
                    } else {
                        ErrorPopup.showError("Invalid move. Check distance or remaining moves.");
                    }
                } else if (shipsMoved.get() >= amount) {
                    ErrorPopup.showError("You have moved the maximum number of ships.");
                }
            });
        }
    }

    /**
     * Garantit que chaque hexes ne contienne pas plus de vaisseaux qu'il n'est autorisé à en contenir.
     * S'il y a trop de vaisseaux, ceux en excès sont supprimés.
     */
    private void sustainShips() {
        for (HexView hexView : gameBoardController.getHexViews()) {
            Hex hex = hexView.getHex();
            int currentShipCount = hex.getShips().size();
            int maxShipsAllowed = 1 + hex.getSystemLevel().ordinal();

            if (currentShipCount > maxShipsAllowed) {
                int excessShips = currentShipCount - maxShipsAllowed;

                for (int i = 0; i < excessShips; i++) {
                    Ship shipToRemove = hex.getShips().get(i);
                    Player owner = shipToRemove.getOwner();

                    owner.removeShip(shipToRemove);
                    hex.removeShip(shipToRemove);

                    Platform.runLater(() -> {
                        for (ShipView shipView : gameBoardController.getShipViews()) {
                            if (shipView.getShip() == shipToRemove) {
                                gameBoardController.getRootLayout().getChildren().remove(shipView.getBody());
                                break;
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * Permet au joueur de choisir un secteur en cliquant sur des hexagones.
     * Si un secteur n'a pas encore été noté, il sera noté et le score du joueur sera mis à jour.
     *
     * @param player Le joueur qui fait le choix.
     * @param latch Le signal qui se déclenche lorsque le joueur a terminé de choisir.
     */
    private void chooseSector(Player player, CountDownLatch latch) {
        for (HexView hexView : gameBoardController.getHexViews()) {
            hexView.getPolygon().setOnMouseEntered(event -> {
                Sector sector = hexView.getHex().getSector();
                for (HexView hexView1 : gameBoardController.getHexViews()) {
                    if (hexView1.getHex().getSector().equals(sector)) {
                        hexView1.getPolygon().setStrokeWidth(3);
                    }
                }
            });

            hexView.getPolygon().setOnMouseExited(event -> {
                Sector sector = hexView.getHex().getSector();
                for (HexView hexView1 : gameBoardController.getHexViews()) {
                    if (hexView1.getHex().getSector().equals(sector)) {
                        hexView1.getPolygon().setStrokeWidth(1);
                    }
                }
            });

            hexView.getPolygon().setOnMouseClicked(event -> {
                Sector sector = hexView.getHex().getSector();
                if (!sector.getIsScored()) {
                    sector.setIsScored(true);
                    countScore(sector, player);
                    latch.countDown();
                } else {
                    ErrorPopup.showError("This sector is already scored.");
                }
            });
        }
    }

    /**
     * Met à jour le score du joueur en fonction du niveau du système des hexagones dans le secteur donné.
     *
     * @param sector Le secteur choisi.
     * @param player Le joueur dont le score va évoluer.
     */
    private void countScore(Sector sector, Player player) {
        for (Hex hex : sector.getHexes()) {
            if (hex.isControlledBy(player)) {
                player.setScore(player.getScore() + hex.getSystemLevel().ordinal());
            }
        }
    }

    /**
     * Trie les joueurs en fonction de la priorité de leurs commandes ("Expand", "Explore", "Exterminate").
     * La liste des joueurs est triée dans l'ordre d'exécution de leurs commandes.
     *
     * @return Une liste des joueurs pour chaque index de commandes.
     */
    private List<List<Player>> sortOrderOfExecution() {
        List<String> commandPriority = Arrays.asList("Expand", "Explore", "Exterminate");
        List<List<Player>> finalOrder = new ArrayList<>();

        int maxCommands = playerCommands.values().stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        for (int commandIndex = 0; commandIndex < maxCommands; commandIndex++) {
            List<Player> currentPlayers = new ArrayList<>();

            for (Map.Entry<Player, List<String>> entry : playerCommands.entrySet()) {
                Player player = entry.getKey();
                List<String> commands = entry.getValue();

                if (commandIndex < commands.size()) {
                    currentPlayers.add(player);
                }
            }

            int finalCommandIndex = commandIndex;
            currentPlayers.sort((p1, p2) -> {
                String command1 = playerCommands.get(p1).get(finalCommandIndex);
                String command2 = playerCommands.get(p2).get(finalCommandIndex);
                return Integer.compare(
                        commandPriority.indexOf(command1),
                        commandPriority.indexOf(command2)
                );
            });

            finalOrder.add(currentPlayers);
        }

        return finalOrder;
    }

    /**
     * Détermine l'efficacité d'une commande en se basant sur le nombre de joueurs qui l'ont choisis.
     *
     * @param commandName Le nom de la commande.
     * @param i L'index de l'ordre actuel.
     * @return L'efficacité de la commande (1, 2, ou 3).
     */
    public int getCommandEffectiveness(String commandName, int i) {
        long expandCount = game.getPlayers().stream()
                .map(p -> playerCommands.get(p).get(i))
                .filter(command -> command.equals(commandName))
                .count();

        return switch ((int) expandCount) {
            case 1 -> 3;
            case 2 -> 2;
            default -> 1;
        };
    }

    /**
     * Permet au joueur de sélectionner un vaisseau en cliquant dessus.
     * Si le vaisseau sélectionné appartient au joueur, il est mis en surbrillance.
     *
     * @param player Le joueur qui sélectionne le vaisseau.
     */
    private void selectShipView(Player player) {
        for (ShipView shipView : gameBoardController.getShipViews()) {
            shipView.getBody().setOnMouseClicked(event -> {
                if (shipView.getShip().isOwner(player)) {
                    if (selectedShipView == null) {
                        selectedShipView = shipView;
                        selectedShipView.select();
                    } else {
                        selectedShipView.deselect();
                        selectedShipView = null;
                    }
                } else {
                    ErrorPopup.showError("This is not your ship");
                }
            });
        }
    }
}
