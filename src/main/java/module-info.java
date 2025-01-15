module fr.lasschko.pocketimperium.pocketimperium {
    requires javafx.controls;
    requires javafx.fxml;

    opens fr.lasschko.pocketimperium.pocketimperium.view to javafx.fxml;
    opens fr.lasschko.pocketimperium.pocketimperium to javafx.fxml;
    exports fr.lasschko.pocketimperium.pocketimperium;
    opens fr.lasschko.pocketimperium.pocketimperium.controller to javafx.fxml;
    opens fr.lasschko.pocketimperium.pocketimperium.utils to javafx.fxml;
}