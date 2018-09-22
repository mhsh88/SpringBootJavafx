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
    },
    HEATER {
        @Override
    public String getTitle() {
        return getStringFromResourceBundle("heater.title");
    }

        @Override
        public String getFxmlFile() {
            return "/fxml/Heater.fxml";
        }

    }, AFTER_HEATER{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("afterHeater.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/AfterHeater.fxml";
        }

    }, RUN{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("run.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Run.fxml";
        }


    }, STATION_PROPERTY{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("stationProperty.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/StationProperty.fxml";
        }


    }, SHOW_RESULT{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("showResult.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/ShowChart.fxml";
        }

    }, STATION_SELECT {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("stationSelect.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/StationSelect.fxml";
        }
    }, SEC {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("sec.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Sec.fxml";
        }
    };



    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
