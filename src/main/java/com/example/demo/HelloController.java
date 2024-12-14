package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.StyleClassedTextArea;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

// PROGRAMDA RİCHTEXTBOX VE JAVA 17 KULLANILMIŞTIR LİB/richtextfx-0.10.9.jar BULUNMAKTADIR.
// İNTELLİJ İÇİN RİCHTEXTBOX KURULUMU İÇİN SOL ÜSTTEN PROJECT STRUCTURE > LİBRARİES PROJENİN İÇİNİ KOYDUĞUM .JAR'IN YOLU VE APPLY
// PROJEYİ EĞER AÇAMAZ HATA ALIR İSENİZ BANA ULAŞABİLİRSİNİZ HOCAM
// PROGRAMDA SADECE MAUSE İLE SEÇİLMİŞ BÖLGELERE STİL ÖZELLİĞİ UYGULAMAKTADIR.
// NORMALDE STİL ÖZELLİKLERİNİN ÜST ÜSTE BİNMESİ YANİ YAZI KALIN İKEN HEM DE İTALİK DE OLABİLMESİ İÇİN AYAR YAPACAKTIM FAKAT
// UYUMSUZLUK PROBLEMLERİ YÜZÜNDEN BU İŞLEMİ GERÇEKLEŞTİREMEDİM BİLGİNİZE.
// AHMET EGE SANDAL 2. SINIF 2.ŞUBE 231030015

public class HelloController {
    @FXML
    public ComboBox<String> textColorComboBox;
    @FXML
    public ComboBox<String> backgroundColorComboBox;
    @FXML
    private StyleClassedTextArea styledTextArea;
    @FXML
    private ComboBox<String> fontFamilyComboBox;
    @FXML
    private ComboBox<String> fontSizeComboBox;
    @FXML
    private Button alignLeftButton;
    @FXML
    private Button alignCenterButton;
    @FXML
    private Button alignRightButton;
    @FXML
    private ImageView logoImageView;
    @FXML
    private Stage stage;
    @FXML
    private File currentFile;

    @FXML
    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnShown(e -> {
            Scene scene = stage.getScene();
            if (scene != null) {
                defineShortcuts(scene);
            }
        });
    }

    @FXML
    public void initialize() {
        Image alignLeftIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/align-left.png")));
        Image alignCenterIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/align-center.png")));
        Image alignRightIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/align-right.png")));

        alignLeftButton.setGraphic(createImageView(alignLeftIcon));
        alignCenterButton.setGraphic(createImageView(alignCenterIcon));
        alignRightButton.setGraphic(createImageView(alignRightIcon));

        styledTextArea.setStyle("-fx-font-family: Arial; -fx-font-size: 16px; -fx-text-fill: black; -fx-background-color: white;");
        fontFamilyComboBox.setValue("Arial");
        fontSizeComboBox.setValue("16");
        textColorComboBox.setValue("Black");
        backgroundColorComboBox.setValue("White");

        styledTextArea.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();

            if (event.isControlDown() || event.isAltDown() || event.isMetaDown()) {
                return;
            }

            if (character.isEmpty() || character.equals("\b") || character.equals("\r")) {
                return;
            }

            if (!isAllowedCharacter(character)) {
                event.consume();
                Platform.runLater(() -> showInvalidCharacterAlert(character));
            }
        });

        Image logo = new Image(Objects.requireNonNull(getClass().getResource("/icons/icon.jpg")).toExternalForm());
        logoImageView.setImage(logo);

        textColorComboBox.setCellFactory(comboBox -> new ListCell<String>() {
            private final Rectangle rectangle;
            {
                rectangle = new Rectangle(16, 16);
            }

            @Override
            protected void updateItem(String colorName, boolean empty) {
                super.updateItem(colorName, empty);

                if (empty || colorName == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    rectangle.setFill(Color.valueOf(colorName));
                    setGraphic(rectangle);
                    setText(colorName);
                }
            }
        });

        textColorComboBox.setButtonCell(new ListCell<String>() {
            private final Rectangle rectangle = new Rectangle(16, 16);

            @Override
            protected void updateItem(String colorName, boolean empty) {
                super.updateItem(colorName, empty);

                if (empty || colorName == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    rectangle.setFill(Color.valueOf(colorName));
                    setGraphic(rectangle);
                    setText(colorName);
                }
            }
        });

    }

    @FXML
    private boolean isAllowedCharacter(String character) {
        return character.matches("[a-zA-Z0-9.,?\\s]");
    }

    @FXML
    private void showInvalidCharacterAlert(String invalidChar) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Geçersiz Karakter!");
        alert.setHeaderText(null);
        alert.setContentText("Geçersiz karakter girdiniz: " + invalidChar +
                "\nYalnızca harfler, rakamlar, nokta, virgül ve soru işareti girilebilir.");
        alert.showAndWait();
    }

    @FXML
    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(16);
        imageView.setFitHeight(16);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    @FXML
    protected void onExitMenuItemClick() {
        System.exit(0);
    }

    @FXML
    protected void onAboutMenuItemClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hakkında");
        alert.setHeaderText("Metin Düzenleyici");
        alert.setContentText("Bu program, metin düzenleme, yazı tipi ve renk değiştirme, " +
                "dosya açma/kaydetme ve hizalama özellikleri sunar. Kullanıcı dostu bir arayüzle geliştirilmiştir.");
        alert.showAndWait();
    }

    @FXML
    protected void onOpenFileMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Dosya Aç");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Metin Dosyaları", "*.txt"),
                new FileChooser.ExtensionFilter("Tüm Dosyalar", "*.*")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                styledTextArea.replaceText(content);
                currentFile = file;
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Dosya okunamadı: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    protected void onSaveFileMenuItemClick() {
        if (currentFile == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen önce bir dosya açın!");
            alert.showAndWait();
            return;
        }

        try (FileWriter writer = new FileWriter(currentFile)) {
            writer.write(styledTextArea.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Başarılı");
            alert.setHeaderText(null);
            alert.setContentText("Dosya başarıyla kaydedildi!");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Dosya kaydedilemedi: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    protected void onCloseFileMenuItemClick() {
        if (currentFile == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText(null);
            alert.setContentText("Kapalı bir dosya bulunmamaktadır!");
            alert.showAndWait();
        } else {
            currentFile = null;
            styledTextArea.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bilgi");
            alert.setHeaderText(null);
            alert.setContentText("Dosya kapatıldı!");
            alert.showAndWait();
        }
    }

    @FXML
    public void onFontFamilyChange() {
        String selectedFont = fontFamilyComboBox.getValue();
        if (selectedFont != null) {
            int start = styledTextArea.getSelection().getStart();
            int end = styledTextArea.getSelection().getEnd();
            if (start != end) {
                String styleClass = getFontFamilyStyleClass(selectedFont);
                styledTextArea.setStyleClass(start, end, styleClass);
            }
        }
    }

    @FXML
    public void onFontSizeChange() {
        String selectedSize = fontSizeComboBox.getValue();
        if (selectedSize != null) {
            int start = styledTextArea.getSelection().getStart();
            int end = styledTextArea.getSelection().getEnd();
            if (start != end) {
                String styleClass = getFontSizeStyleClass(selectedSize);
                styledTextArea.setStyleClass(start, end, styleClass);
            }
        }
    }

    private String getFontFamilyStyleClass(String fontFamily) {
        return switch (fontFamily) {
            case "Arial" -> "font-family-arial";
            case "Verdana" -> "font-family-verdana";
            case "Tahoma" -> "font-family-tahoma";
            default -> "";
        };
    }

    private String getFontSizeStyleClass(String fontSize) {
        return switch (fontSize) {
            case "12" -> "font-size-12";
            case "14" -> "font-size-14";
            case "16" -> "font-size-16";
            case "18" -> "font-size-18";
            case "20" -> "font-size-20";
            default -> "";
        };
    }



    @FXML
    public void onBoldButtonClick() {
        String styleClass = "bold";
        applyStyleToSelectedText(styleClass);
    }

    @FXML
    public void onItalicButtonClick() {
        String styleClass = "italic";
        applyStyleToSelectedText(styleClass);
    }

    @FXML
    public void onUnderlineButtonClick() {
        String styleClass = "underline";
        applyStyleToSelectedText(styleClass);
    }

    @FXML
    public void onNormalButtonClick() {
        String styleClass = "normal";
        applyStyleToSelectedText(styleClass);
    }

    private void applyStyleToSelectedText(String styleClass) {
        int start = styledTextArea.getSelection().getStart();
        int end = styledTextArea.getSelection().getEnd();
        if (start != end) {
            styledTextArea.setStyleClass(start, end, styleClass);
        }
    }


    @FXML
    public void onTextColorChange() {
        String selectedColor = textColorComboBox.getValue();
        if (selectedColor != null) {
            String cssClass = selectedColor.toLowerCase() + "-text";
            int selectionStart = styledTextArea.getSelection().getStart();
            int selectionEnd = styledTextArea.getSelection().getEnd();
            if (selectionStart == selectionEnd) {
                System.out.println("Lütfen bir metin seçin.");
                return;
            }
            styledTextArea.clearStyle(selectionStart, selectionEnd);
            styledTextArea.setStyleClass(selectionStart, selectionEnd, cssClass);
        }
    }

    @FXML
    public void onBackgroundColorChange() {
        String selectedColor = backgroundColorComboBox.getValue();
        if (selectedColor != null) {
            String cssClass = getBackgroundColorStyleClass(selectedColor);
            int selectionStart = styledTextArea.getSelection().getStart();
            int selectionEnd = styledTextArea.getSelection().getEnd();
            if (selectionStart == selectionEnd) {
                System.out.println("Lütfen bir metin seçin.");
                return;
            }
            styledTextArea.clearStyle(selectionStart, selectionEnd);
            styledTextArea.setStyleClass(selectionStart, selectionEnd, cssClass);
            System.out.println("Seçili aralık: " + selectionStart + " - " + selectionEnd);
        }
    }

    private String getBackgroundColorStyleClass(String color) {
        return switch (color) {
            case "Light Gray" -> "lightgray-bg";
            case "Light Yellow" -> "lightyellow-bg";
            case "Light Blue" -> "lightblue-bg";
            case "White" -> "white-bg";
            default -> "white-bg";
        };
    }

    @FXML
    protected void onSaveAsFileMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Metin Dosyaları", "*.txt"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(styledTextArea.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Başarılı");
                alert.setHeaderText(null);
                alert.setContentText("Dosya başarıyla kaydedildi: " + file.getAbsolutePath());
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Dosya kaydedilemedi: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void onAlignLeft() {
        applyParagraphAlignment("align-left");
    }

    public void onAlignCenter() {
        applyParagraphAlignment("align-center");
    }

    public void onAlignRight() {
        applyParagraphAlignment("align-right");
    }

    private void applyParagraphAlignment(String alignmentClass) {
        int paragraphIndex = styledTextArea.currentParagraphProperty().getValue();
        styledTextArea.setParagraphStyle(paragraphIndex, javafx.collections.FXCollections.observableArrayList(alignmentClass));
        System.out.println("Paragraf: " + paragraphIndex + ", Hizalama: " + alignmentClass);
    }


    @FXML
    public void onCopyButtonClick() {
        String selectedText = styledTextArea.getSelectedText();
        if (!selectedText.isEmpty()) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(selectedText);
            clipboard.setContent(content);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bilgi");
            alert.setHeaderText(null);
            alert.setContentText("Seçili metin panoya kopyalandı!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText(null);
            alert.setContentText("Kopyalamak için metin seçmelisiniz!");
            alert.showAndWait();
        }
    }

    @FXML
    public void onPasteButtonClick() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasString()) {
            String clipboardText = clipboard.getString();
            int caretPosition = styledTextArea.getCaretPosition();
            styledTextArea.insertText(caretPosition, clipboardText);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bilgi");
            alert.setHeaderText(null);
            alert.setContentText("Panodaki metin yapıştırıldı!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText(null);
            alert.setContentText("Panoda yapıştırılacak bir metin yok!");
            alert.showAndWait();
        }
    }

    public void defineShortcuts(Scene scene) {
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),
                () -> {
                    System.out.println("Ctrl+S kısayolu çalıştı.");
                    onSaveFileMenuItemClick();
                }
        );


        scene.getAccelerators().put(
                new KeyCodeCombination(
                        KeyCode.S,
                        KeyCombination.CONTROL_DOWN,
                        KeyCombination.SHIFT_DOWN
                ),
                this::onSaveAsFileMenuItemClick
        );

        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN),
                this::onOpenFileMenuItemClick
        );

        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN),
                this::onExitMenuItemClick
        );

        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN),
                this::onCloseFileMenuItemClick
        );
    }
}
