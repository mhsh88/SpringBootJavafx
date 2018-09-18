package com.codetreatise.view;

import java.util.ResourceBundle;

public enum FxmlView {

    USER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("user.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/User.fxml";
        }
    },
    LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Login.fxml";
        }
    },
    STATION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("station.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Station.fxml";
        }
    },
    BEFORE_HEATER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("beforeHeater.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/BeforeHeater.fxml";
        }
    }
    ;



    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
