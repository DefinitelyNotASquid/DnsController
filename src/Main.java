
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URL;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.util.Duration;
import okhttp3.*;

/**
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class Main extends Application {

    public  TextField account_id = new TextField();
    public  TextField email_Address = new TextField();
    public  PasswordField password_API = new PasswordField();
    public  TextField domain = new TextField();
    public  TextField recordID = new TextField();
    public  TextField name = new TextField();
    public  TextField ipAddress = new TextField();
    public  TextField update_Frequency = new TextField();


    public void start(Stage primaryStage) throws Exception {
        Stage window = primaryStage;

        loadSettings();


        window.setTitle("DNS Simple Updater");

        GroupBox TopGroup = new GroupBox();

        TopGroup.setText("Auto Startup");

        GridPane topGridPane = new GridPane();
        ColumnConstraints cc = new ColumnConstraints();
        cc.setMinWidth(34);
        topGridPane.getColumnConstraints().add(cc);

        TopGroup.setContent(topGridPane);


        CheckBox autostart = new CheckBox();
        Label autostart_Label = new Label("Automatically Start DNS Simple Updater on logon");

        topGridPane.add(autostart_Label, 1, 0);
        topGridPane.add(autostart, 0, 0);


        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab();
        tab1.setText("General");
        tabPane.getTabs().add(tab1);
        tab1.setClosable(false);

        GridPane MainBorderPane = new GridPane();
        tab1.setContent(MainBorderPane);


        GroupBox DNS_Credentials = new GroupBox();
        GroupBox DNS_Record = new GroupBox();
        GroupBox Schedule = new GroupBox();

        DNS_Credentials.setText("DNSimple Credentials");
        GridPane dNS_Credentials_Grid = new GridPane();
        DNS_Credentials.setContent(dNS_Credentials_Grid);

        ColumnConstraints DNS_Credentials_CC = new ColumnConstraints();
        DNS_Credentials_CC.setMinWidth(150);
        dNS_Credentials_Grid.getColumnConstraints().add(DNS_Credentials_CC);


        Label email_Address_Label = new Label("Email Address:  ");
        Label password_API_Label = new Label("Password / API Token:  ");
        Label account_id_Label = new Label("Account Id:  ");



        email_Address.setPromptText("Enter your email address");
        password_API.setPromptText("Enter your API token");
        account_id.setPromptText("Enter Account Id");

        dNS_Credentials_Grid.add(email_Address_Label, 0, 0);
        dNS_Credentials_Grid.add(email_Address, 1, 0);
        dNS_Credentials_Grid.add(password_API_Label, 0, 1);
        dNS_Credentials_Grid.add(password_API, 1, 1);
        dNS_Credentials_Grid.add( account_id_Label, 0,2);
        dNS_Credentials_Grid.add( account_id, 1,2);

        MainBorderPane.add(DNS_Credentials, 0, 1);

        DNS_Record.setText("DNS Record");

        GridPane dNS_Record_Grid = new GridPane();
        DNS_Record.setContent(dNS_Record_Grid);

        ColumnConstraints DNS_Record_CC = new ColumnConstraints();
        DNS_Record_CC.setMinWidth(150);
        dNS_Record_Grid.getColumnConstraints().add(DNS_Record_CC);

        Label domain_Label = new Label("Domain:  ");
        Label recordID_Label = new Label("Record ID:  ");
        Label name_Label = new Label("Name:  ");
        Label ipAddress_Label = new Label("IP Adress:  ");



        domain.setPromptText("Enter Domain Name");
        recordID.setPromptText("Enter Record Id");
        name.setPromptText("Enter Name");


        dNS_Record_Grid.add(domain_Label, 0, 0);
        dNS_Record_Grid.add(recordID_Label, 0, 1);
        dNS_Record_Grid.add(name_Label, 0, 2);
        dNS_Record_Grid.add(ipAddress_Label, 0, 3);

        dNS_Record_Grid.add(domain, 1, 0);
        dNS_Record_Grid.add(recordID, 1, 1);
        dNS_Record_Grid.add(name, 1, 2);
        dNS_Record_Grid.add(ipAddress, 1, 3);

        MainBorderPane.add(DNS_Record, 0, 2);


        GridPane dns_schedule_grid = new GridPane();
        Schedule.setText("Schedule");
        Label update_Frequency_Label = new Label("Update Frequencey:  ");

        update_Frequency.setPromptText("Frequency in Min");

        dns_schedule_grid.getColumnConstraints().add(DNS_Credentials_CC);

        dns_schedule_grid.add(update_Frequency_Label, 0, 0);
        dns_schedule_grid.add(update_Frequency, 1, 0);

        Schedule.setContent(dns_schedule_grid);
        MainBorderPane.add(Schedule, 0, 3);

        MainBorderPane.add(TopGroup, 0, 0);


        Button Ok = new Button("Ok");
        Button Cancel = new Button("Cancel");
        Ok.setMinWidth(60);
        Cancel.setMinWidth(60);

        HBox toolbarhbox = new HBox();
        toolbarhbox.getChildren().addAll(Ok, Cancel);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        ipAddress.setText(getIp());
        ipAddress.setEditable(false);

        ToolBar toolBar = new ToolBar(

                region2, toolbarhbox


        );
        Ok.setOnAction(e -> {

            saveSettings();
            System.out.println("Trying to update");
            try {
                UpdateDnsRecord(recordID.getText(), domain.getText(), account_id.getText(), password_API.getText(), ipAddress.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }


        });


        Cancel.setOnAction(e -> {

            saveSettings();
            window.close();


        });


        MainBorderPane.add(toolBar, 0, 4);

        Scene MainScene = new Scene(tabPane, 320, 460);


        window.initStyle(StageStyle.DECORATED);
        window.setResizable(false);
        window.setAlwaysOnTop(true);
        window.setScene(MainScene);
        window.show();


    }
    public static String getIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void UpdateDnsRecord( String recordId, String domain, String accountId, String password, String ip ) throws IOException {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"content\": \""+ ip + "\"}");

        Request request = new Request.Builder()
                .url("https://api.dnsimple.com/v2/"+accountId+"/zones/"+ domain +"/records/"+recordId)
                .patch(body)
                .addHeader("Authorization", "Bearer "+ password)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response);

    }
    public void saveSettings(){

        new File(System.getProperty("user.dir") + "/Settings/").mkdirs();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(

                /*
                   Output the settings file to the directory
                */
                new FileOutputStream(System.getProperty("user.dir") + "/Settings/Settings.ini"), "utf-8"))) {

            String s = email_Address.getText() + "\n" + password_API.getText() + "\n" + account_id.getText() + "\n" + domain.getText() + "\n" +recordID.getText() + "\n" + name.getText() + "\n" + update_Frequency.getText();

            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadSettings() throws IOException {

        if( new File(System.getProperty("user.dir") + "/Settings/Settings.ini").exists()){

            BufferedReader brTest = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/Settings/Settings.ini"));
            email_Address.setText( brTest .readLine());
            password_API.setText( brTest .readLine());
            account_id.setText( brTest .readLine());
            domain.setText( brTest .readLine());
            recordID.setText( brTest .readLine());
            name.setText( brTest .readLine());
            update_Frequency.setText( brTest .readLine());


        };


    }




}
