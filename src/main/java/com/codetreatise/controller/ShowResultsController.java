package com.codetreatise.controller;

import com.codetreatise.config.StageManager;
import com.codetreatise.view.FxmlView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import ir.behinehsazan.gasStation.model.gas.Gas;
import ir.behinehsazan.gasStation.model.station.StationLogic;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import sample.model.showResultEntity.Table;
import sample.util.FileLocation;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ShowResultsController implements Initializable {
    public Button backButton;
    public Accordion accordion;
    ObjectMapper mapper = new ObjectMapper();

    public AnchorPane mainAnchor;
    @Lazy
    @Autowired
    StageManager stageManager;

    public TextField textField2;
    public GridPane gridPane;

    private JsonNode stationLogicsNode;


    @FXML
    TableView<Table> tableID;
    @FXML
    TableView<Table> hydrateTable;

    @FXML
    TableColumn<Table, String> name;
    @FXML
    TableColumn<Table, String> meter;
    @FXML
    TableColumn<Table, String> value;

//    TreeItem<String> pressureItem = new TreeItem<>("Psi");
//    TreeItem<String> tempretureItem = new TreeItem<>("F");

    @FXML
    TableColumn<Table, String> hydrateName;
    @FXML
    TableColumn<Table, String> hydrateMeter;
    @FXML
    TableColumn<Table, String> hydrateValue;

    //    public TableView table;
    @FXML
    TextField textField = new TextField();

    public JsonNode getStationLogicsNode() {
        return stationLogicsNode;
    }

    public void setStationLogicsNode(JsonNode stationLogicsNode) {
        this.stationLogicsNode = stationLogicsNode;
    }

    public static ObservableList<Table> getData() {
        return data;
    }
    public static ObservableList<Table> getHydrateData() {return dataWithThydrate;}

    static final ObservableList<Table> data = FXCollections.observableArrayList();
    static final ObservableList<Table> dataWithThydrate = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        accordion.getPanes().remove(0,accordion.getPanes().size());


        for(JsonNode stationLogic : stationLogicsNode){


            TitledPane titledPane = new TitledPane();
            if(stationLogic.get("message") != null && stationLogic.get("message").toString().equals("\"T_h\""))
                titledPane.setText("دمای هیدرات");
            else
                titledPane.setText("اطلاعات ورودی");


            Accordion childAccordion = getTitledPaneContent(stationLogic);

//            AnchorPane anchorPane = new AnchorPane();
//            TitledPane titledPane1 = new TitledPane();
//            titledPane1.setText("اول");
//            anchorPane.getChildren().add(titledPane1);
//            TitledPane titledPane2 = new TitledPane();
//            titledPane1.setText("دوم");
//            anchorPane.getChildren().add(titledPane2);
            titledPane.setContent(childAccordion);

            accordion.getPanes().add(titledPane);
        }



    }

    private Accordion getTitledPaneContent(JsonNode stationLogic) {
        Accordion accordion = new Accordion();
//        AnchorPane anchorPane = new AnchorPane();

        Iterator<String> stringIterator = stationLogic.fieldNames();
        for (Iterator<String> it = stringIterator; it.hasNext(); ) {
            String string = it.next();
            TitledPane titledPane = null;
            switch (string){
                case "beforeHeater":
                    titledPane = setBeforeHeater(stationLogic.get(string),stationLogic.get("calBeforeHeater"));
                    break;
                case "heaters":
                    titledPane = setHeaterTitledPane(stationLogic.get(string));
                    break;
                case "afterHeater":
                    titledPane = setAfterHeater(stationLogic.get(string),stationLogic.get("calAfterHeater"));
                    break;
                case "collector":
                    titledPane = setCollector(stationLogic.get(string));
                    break;
                case "runs":
                    titledPane = setRuns(stationLogic.get(string));
                    break;
                case "regulator":
                    titledPane = setRegulator(stationLogic.get(string));
                    break;
                default:
                    break;
            }


            if (titledPane != null) {
                accordion.getPanes().add(titledPane);
            }
        }
        return accordion;
    }

    private TitledPane setRegulator(JsonNode jsonNode) {
        TitledPane titledPane = new TitledPane();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(getInputOutput(jsonNode));
        titledPane.setContent(anchorPane);

        titledPane.setText("رگولاتور");
        return titledPane;
    }

    private TitledPane setRuns(JsonNode jsonNode) {
        TitledPane titledPane = new TitledPane();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(getInputOutput(jsonNode));
        titledPane.setContent(anchorPane);

        titledPane.setText("ران‌ها");
        return titledPane;

    }

    private TitledPane setCollector(JsonNode jsonNode) {
        TitledPane titledPane = new TitledPane();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(getInputOutput(jsonNode));
        titledPane.setContent(anchorPane);

        titledPane.setText("کلکتور");
        return titledPane;
    }

    private TitledPane setAfterHeater(JsonNode jsonNode, JsonNode calAfterHeater) {
        TitledPane titledPane = new TitledPane();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(getInputOutput(jsonNode));
        titledPane.setContent(anchorPane);

        titledPane.setText("خط لوله بعد از گرم‌کن");
        return titledPane;
    }

    private TitledPane setHeaterTitledPane(JsonNode jsonNode) {
        TitledPane titledPane = new TitledPane();
        AnchorPane anchorPane = new AnchorPane();
        VBox vBox = new VBox();
        vBox.getChildren().add(getInputOutput(jsonNode));
        JFXTextField consumptionTextField = new JFXTextField();
        consumptionTextField.setEditable(false);
        consumptionTextField.setText(String.valueOf(Math.round(jsonNode.get("consumption").asDouble())));
        vBox.getChildren().add(consumptionTextField);
        anchorPane.getChildren().add(vBox);

        titledPane.setContent(anchorPane);

        titledPane.setText("گرم‌کن");

        return titledPane;
    }

    private TitledPane setBeforeHeater(JsonNode jsonNode, JsonNode calBeforeHeater) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(getInputOutput(jsonNode));
        if(calBeforeHeater!=null){
            HBox hBox = new HBox();
            JFXTextField withInsulationTextBox = new JFXTextField();
            withInsulationTextBox.setEditable(false);
//            withInsulationTextBox.setText(String.valueOf(calBeforeHeater.get("withInsulationConsumption").asDouble()));
//            hBox.getChildren().add()

        }


        TitledPane titledPane = new TitledPane();
        titledPane.setText("خط لوله قبل از گرم‌کن");
        titledPane.setContent(anchorPane);
        return titledPane;
    }

    private Accordion getInputOutput(JsonNode jsonNode) {
        Accordion accordion = new Accordion();
        TitledPane input = new TitledPane();
        input.setText("ورودی");
        input.setContent(getGasElementsPane(jsonNode.get("tin"),jsonNode.get("pin")));
        TitledPane output = new TitledPane();
        output.setText("خروجی");
        output.setContent(getGasElementsPane(jsonNode.get("tout"),jsonNode.get("pout")));
        accordion.getPanes().add(input);
        accordion.getPanes().add(output);

        return accordion;
    }

    private AnchorPane getGasElementsPane(JsonNode t, JsonNode p) {
        double T = t.asDouble();
        double P = p.asDouble();
        Gas gas = new Gas();
        gas.calculate(P,T, stageManager.getCityGateStationEntity().getGasEntity().getComponent());
//        JsonNode gasNode = mapper.valueToTree(gas);
        JsonNode gasNode = null;
        try {
            gasNode = getCopyGas(gas);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane anchorPane = new AnchorPane();
        JFXTextField gasPropertyTextBox = new JFXTextField();
        gasPropertyTextBox.setEditable(false);
        gasPropertyTextBox.promptTextProperty().setValue("خصوصیت گاز");
        gasPropertyTextBox.setMinSize(30.0,20.0);
        HBox hBox = new HBox();
        hBox.setMinHeight(25.0);
        JFXTextField pressureTextBox = new JFXTextField(String.valueOf(P));
        pressureTextBox.setEditable(false);
        JFXTextField temperatureTextBox = new JFXTextField(String.valueOf(T));
        temperatureTextBox.setEditable(false);
        JFXComboBox<String> gasPropertyCombobox = new JFXComboBox<>();

        Iterator<String> stringIterator = gasNode.fieldNames();
        for (Iterator<String> it = stringIterator; it.hasNext(); ) {
            gasPropertyCombobox.getItems().add(it.next());
        }

        JsonNode finalGasNode = gasNode;
        gasPropertyTextBox.textProperty().bind(Bindings.createStringBinding(()->{
            if(gasPropertyCombobox.getSelectionModel().getSelectedItem() == null){
                return "";
            }

            return finalGasNode.get(String.valueOf(gasPropertyCombobox.getSelectionModel().getSelectedItem())).toString();
            },
                gasPropertyCombobox.getSelectionModel().selectedItemProperty()));

        gasPropertyCombobox.getSelectionModel().select("z");



        hBox.getChildren().add(gasPropertyTextBox);
        hBox.getChildren().add(gasPropertyCombobox);
        hBox.getChildren().add(temperatureTextBox);
        hBox.getChildren().add(pressureTextBox);
        anchorPane.getChildren().add(hBox);



        return anchorPane;
    }
    private ObjectNode getCopyGas(Gas gas) throws IOException {
        TokenBuffer tb = new TokenBuffer(null, false);
        mapper.writeValue(tb, gas);
        Gas copyGas = mapper.readValue(tb.asParser(), Gas.class);
        copyGas.setComponent(gas.getComponent());
        ObjectNode gasNode = (ObjectNode) mapper.readTree(mapper.writeValueAsString(copyGas));
        return  gasNode;
    }

    public void customResize(TableView<?> view) {

        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth() + ((tableWidth - width.get()) / view.getColumns().size()));
            });
        }
    }


    public void saveAction(ActionEvent actionEvent) throws IOException {

        FileChooser chooser = new FileChooser();
//        FileChooserBuilder fcb = FileChooserBuilder.create();
//        FileChooser fc = fcb.title("Open Dialog").build();
        chooser.setTitle("Select File");

//Set extension filter
        FileChooser.ExtensionFilter extFilterALL =
                new FileChooser.ExtensionFilter("All Files", "*.xls", "*.pdf",
                        "*.PDF");
        FileChooser.ExtensionFilter extFilterExcel =
                new FileChooser.ExtensionFilter("Excel files (*.xls)", "*.xls");
        FileChooser.ExtensionFilter extFilterPDF =
                new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.PDF", "*.pdf");

        chooser.getExtensionFilters().addAll(extFilterALL, extFilterExcel, extFilterPDF);
        chooser.setInitialDirectory(new FileLocation().getFileLocation());
        
        chooser.setInitialFileName("reports");
        
        File file = chooser.showSaveDialog(new Stage());

        ObjectProperty<String> d = chooser.initialFileNameProperty();
        FileChooser.ExtensionFilter zxc = chooser.getSelectedExtensionFilter();
        List<String> sd = zxc.getExtensions();
        if (file == null){
            return;
        }
        String dir="";
        if (file.isDirectory())
        {
            dir=file.getAbsolutePath();
        }
        else
        {
            dir=file.getAbsolutePath().replaceAll(file.getName(), "");
        }

        chooser.getSelectedExtensionFilter().getExtensions().get(0).substring(2).equals("PDF");
        String asfghsdf = chooser.getSelectedExtensionFilter().getExtensions().get(0);
        boolean sdf = chooser.getSelectedExtensionFilter().getExtensions().get(0).substring(2).equals("XLS");



        String fileExtension = null;
        String fileName = null;
        if (file != null) {

            fileName = file.getName();
            fileExtension = fileName.substring(fileName.indexOf(".") + 1, file.getName().length());
//            System.out.println(">> fileExtension " + fileExtension);

        } else {
            return;
        }
        if (chooser.getSelectedExtensionFilter().getExtensions().get(0).substring(2).equals("xls")) {

//            ExcelTest et = new ExcelTest();
//            et.excelTest(file.toString(), tableID,"محاسبات بر اساس دمای وارد شده");
//            et.excelTest(file.toString(), hydrateTable,"محاسبات بر اساس دمای هیدرات به علاوه ۲ درجه");

            HSSFWorkbook workbook = new HSSFWorkbook();


            HSSFSheet spreadsheet = workbook.createSheet("result");
            addImage(workbook, spreadsheet);


//            Row row = spreadsheet.createRow(5);
//
//            for (int j = 0; j < tableID.getColumns().size(); j++) {
//                row.createCell(j).setCellValue(tableID.getColumns().get(j).getText());
//            }
            Row row = spreadsheet.createRow(6);
            row.createCell(0).setCellValue("محاسبات بر اساس اطلاعات وارد شده");

            spreadsheet.addMergedRegion(new CellRangeAddress(
                    6, //first row (0-based)
                    6, //last row  (0-based)
                    0, //first column (0-based)
                    2  //last column  (0-based)
            ));



            for (int i = 0; i < tableID.getItems().size(); i++) {

                row = spreadsheet.createRow(i + 7);

                for (int j = 0; j < tableID.getColumns().size(); j++) {

                    if (tableID.getColumns().get(j).getCellData(i) != null) {

                        row.createCell(j).setCellValue(tableID.getColumns().get(j).getCellData(i).toString());

                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }

            for(int i = 0; i< hydrateTable.getItems().size(); i++){

                row = spreadsheet.createRow(tableID.getItems().size() + i + 5 + 7);

                for (int j = 0; j < hydrateTable.getColumns().size(); j++) {

                    if (hydrateTable.getColumns().get(j).getCellData(i) != null) {

                        row.createCell( j).setCellValue(hydrateTable.getColumns().get(j).getCellData(i).toString());

                        if (i == 0 && j == 2){
                            spreadsheet.addMergedRegion(new CellRangeAddress(
                                    row.getRowNum(), //first row (0-based)
                                    row.getRowNum(), //last row  (0-based)
                                    0, //first column (0-based)
                                    row.getLastCellNum() - 1  //last column  (0-based)
                            ));
//                            row.createCell(1);
//
//                            HSSFCell cell = (HSSFCell) row.getCell(1);
//                            cell.setCellValue(hydrateTable.getColumns().get(j).getCellData(i).toString());
                            row.createCell( 0).setCellValue(hydrateTable.getColumns().get(j).getCellData(i).toString());
                        }


                    } else {

                        row.createCell( j).setCellValue("");

                    }
                }

            }
            HSSFHeader header = spreadsheet.getHeader();
            header.setCenter(HSSFHeader.font("Calibri", "regular") +
                    HSSFHeader.fontSize((short) 14) + "My " + HSSFHeader.startBold() + "Styled" +
                    HSSFHeader.endBold() + " Text with page number " + HSSFHeader.page());

            FileOutputStream fileOut = new FileOutputStream(file.getParent()+"/"+file.getName().split("[.]")[0] + ".xls");
            workbook.write(fileOut);
            fileOut.close();
        } else if (chooser.getSelectedExtensionFilter().getExtensions().get(0).substring(2).equals("PDF")) {

            try {
                OutputStream pdffile = new FileOutputStream(new File(file.getParent() + "/" +file.getName().toString().split("[.]")[0]+".pdf"));

                Document document = new Document();
                PdfWriter.getInstance(document, pdffile);

                document.open();
                Font f = FontFactory.getFont("/sample/view/showResult/font/BNazanin.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//


                document.addAuthor("Mohammad Hossein Sharifi");
                document.addCreationDate();
                document.addCreator("BEHINEH SAZAN SANAT TASISAT");
                document.addTitle("City Gate Station Gas Consumption Calculation");


                //Create Paragraph
                Paragraph paragraph = new Paragraph("CGS gas Consumption Calculation", new Font(Font.FontFamily.TIMES_ROMAN, 18,
                        Font.BOLD));

                paragraph.add(new Paragraph(" "));
//                document.add(new Paragraph("First iText PDF"));




                paragraph.add(new Paragraph("Behineh Sazan Sanat Tasisat"));
                paragraph.add(new Paragraph(" "));
                document.add(paragraph);
                document.add(new Paragraph(new Date().toString()));
                document.add(new Paragraph(new Phrase("    ")));

                //Create a table in PDF
                PdfPTable pdfTable = new PdfPTable(2);
                PdfPCell cell1 = new PdfPCell(new Phrase("مقدار" , f));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                pdfTable.addCell(cell1);

                cell1 = new PdfPCell(new Phrase("نام" , f));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                pdfTable.addCell(cell1);

                pdfTable.setHeaderRows(1);


                pdfTable = addTableToPdfPTable(pdfTable, tableID, f);
                pdfTable = addTableToPdfPTable(pdfTable, hydrateTable, f);



                document.add(pdfTable);

                document.close();
                pdffile.close();

            } catch (DocumentException e) {
                e.printStackTrace();
            }

//        Platform.exit();
        }

        new FileLocation().setFileLocation(dir);
    }
    private PdfPTable addTableToPdfPTable(PdfPTable pdfPTable, TableView<Table> table, Font f){
        PdfPCell cell1;

        for (int i = 0; i < table.getItems().size(); i++) {
            for (int j = 0; j < table.getColumns().size(); j++) {
                if (tableID.getColumns().get(j).getCellData(i) != null) {
                    cell1 = new PdfPCell(new Phrase(table.getColumns().get(j).getCellData(i).toString(), f));
                    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                } else {
                    cell1 = new PdfPCell(new Phrase("", f));
                    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                }
                pdfPTable.addCell(cell1);
            }
        }

        return pdfPTable;

    }

    public void closeAction(ActionEvent actionEvent) {
//        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        stageManager.switchScene(FxmlView.STATION);
    }

    public void addImage(Workbook wb, Sheet sheet){

        try {

//            Workbook wb = new XSSFWorkbook();
//            Sheet sheet = wb.createSheet("My Sample Excel");
            //FileInputStream obtains input bytes from the image file
            InputStream inputStream = getClass().getResourceAsStream("/sample/view/base/logo.png");

//                InputStream inputStream = new FileInputStream("/sample/view/base/logo.png");
            //Get the contents of an InputStream as a byte[].
            byte[] bytes = IOUtils.toByteArray(inputStream);
            //Adds a picture to the workbook
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            //close the input stream
            inputStream.close();
            //Returns an object that handles instantiating concrete classes
            CreationHelper helper = wb.getCreationHelper();
            //Creates the top-level drawing patriarch.
            Drawing<Shape> drawing = (Drawing<Shape>) sheet.createDrawingPatriarch();

            //Create an anchor that is attached to the worksheet
            ClientAnchor anchor = helper.createClientAnchor();

            //create an anchor with upper left cell _and_ bottom right cell
            anchor.setCol1(0); //Column B
            anchor.setRow1(0); //Row 3
            anchor.setCol2(4); //Column C
            anchor.setRow2(5); //Row 4

            //Creates a picture
            Picture pict = drawing.createPicture(anchor, pictureIdx);

            //Reset the image to the original size
            //pict.resize(); //don't do that. Let the anchor resize the image!

            //Create the Cell B3
//                Cell cell = sheet.createRow(2).createCell(1);

            sheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    4, //last row  (0-based)
                    0, //first column (0-based)
                    3  //last column  (0-based)
            ));

            //set width to n character widths = count characters * 256
            //int widthUnits = 20*256;
            //sheet.setColumnWidth(1, widthUnits);

            //set height to n points in twips = n * 20
            //short heightUnits = 60*20;
            //cell.getRow().setHeight(heightUnits);

            Row row = sheet.createRow(0);
            Cell cell = row.createCell(4);
            cell.setCellValue("شرکت بهینه سازان صنعت تاسیسات");
            CellStyle style = wb.createCellStyle();
//            style.setAlignment(CellStyle.ALIGN_CENTER);
//            style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
            org.apache.poi.ss.usermodel.Font font = wb.createFont();
//            font.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
            font.setFontHeightInPoints((short) 18);
            style.setFont(font);
            cell.setCellStyle(style);

            sheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    1, //last row  (0-based)
                    4, //first column (0-based)
                    9  //last column  (0-based)
            ));

            Row row2  = sheet.createRow(2);
            Cell cell2 = row2.createCell(4);
            cell2.setCellValue("نرم‌افزار محاسبه مصرف ایستگاه‌های تقلیل فشار گاز");
            font.setFontHeightInPoints((short) 14);
            cell2.setCellStyle(style);

           sheet.addMergedRegion(new CellRangeAddress(
                    2, //first row (0-based)
                    3, //last row  (0-based)
                    4, //first column (0-based)
                    9  //last column  (0-based)
            ));







            //Write the Excel file

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private ObjectNode getStationLogicJsonNode(StationLogic stationLogic) throws IOException {
        TokenBuffer tb = new TokenBuffer(null, false);

        mapper.writeValue(tb, stationLogic);
        StationLogic copyStation = mapper.readValue(tb.asParser(), StationLogic.class);
        ObjectNode stationNode = (ObjectNode) mapper.readTree(mapper.writeValueAsString(copyStation));
        return  stationNode;
    }

    public void backButton(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.STATION);
    }
}
