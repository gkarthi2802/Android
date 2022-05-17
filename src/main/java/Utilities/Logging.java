package Utilities;

import org.apache.log4j.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.io.FileUtils;


public class Logging {
    //Initialize Log4j instance
    public static Logger Log = Logger.getLogger(Logging.class.getName());
    private String timeStamp, log_level, logFilePath;
    private boolean tempDirExist = false;
    private String outputDirStr = System.getProperty("user.dir") + "/output/";
    private String tmpDirStr = outputDirStr + "temp";
    private String logFileName = "/automate_" + timeStamp() + ".log";


    public Logging() {
        Log = Logger.getLogger(Logging.class.getName());
        String log_file = createTempDir().toString();
        logFilePath = log_file + logFileName;
    }

    private String timeStamp() {
        timeStamp = new SimpleDateFormat("yy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }

    public void start_log(String testClassName) {
        create_log_file(logFilePath);
        log_level = "info"; // todo : log_level value should be set by config/prop file
        Log.setLevel(set_log_level());
        Log.info(testClassName);
    }

    public void end_log(String testClassName) {
        Log.info(testClassName);
        LogManager.shutdown();
    }

    private void create_log_file(String logFilePath) {
        SimpleLayout layout = new SimpleLayout();
        FileAppender appender = null;
        try {
            appender = new FileAppender(layout, logFilePath, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.addAppender(appender);
    }

    private Level set_log_level() {
        Level log_level = null;
        switch (this.log_level.toLowerCase()) {
            case "info":
                log_level = Level.INFO;
                break;
            case "debug":
                log_level = Level.DEBUG;
                break;
            case "warn":
                log_level = Level.WARN;
                break;
            case "error":
                log_level = Level.ERROR;
                break;
        }
        return log_level;
    }

    public void log(String message) {
        switch (this.log_level.toLowerCase()) {
            case "info":
                Log.info(message);
                break;
            case "debug":
                Log.debug(message);
                break;
            case "warn":
                Log.warn(message);
                break;
            case "error":
                Log.error(message);
                break;
            case "fatal":
                Log.fatal(message);
                break;
            default:
                throw new RuntimeException("undefined log level");
        }
    }

    public void log(String message, String log_level) {
//        method overload for error logs
        switch (log_level.toLowerCase()) {
            case "warn":
                Log.warn(message);
                break;
            case "error":
                Log.error(message);
                break;
            case "fatal":
                Log.fatal(message);
                break;
            default:
                throw new RuntimeException("undefined log level");
        }
    }

    private File createTempDir() {
        File tmpDir = null;
        if (!tempDirExist) {
            if (tmpDirStr == null) {
                throw new RuntimeException("Temp dir is not specified");
            } else {
                tmpDir = new File(tmpDirStr);
                if (!tmpDir.exists()) {
                    boolean created = tmpDir.mkdirs();
                    if (!created) {
                        throw new RuntimeException("Unable to create temp dir " + tmpDir);
                    } else {
                        tempDirExist = true;
                    }
                }
            }
        }
        return tmpDir;
    }

    public void delete_temp_dir() {
        File tmp_dir_path = new File(tmpDirStr);
        try {
            FileUtils.deleteDirectory(tmp_dir_path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean recursive_delete() {
        File path = new File(tmpDirStr);
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    try {
                        Files.delete(f.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    f.delete();
                }
            }
        }
        return path.delete();
    }

    public void merge_to_single_log_file() {
        File final_log_output = null;
        File path = new File(tmpDirStr);
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File f : files) {
                String log_data = null;
                try {
                    log_data = FileUtils.readFileToString(f);
                    if (final_log_output == null) {
                        final_log_output = new File(outputDirStr, f.getName());
                        create_log_file(final_log_output.toString());
                    }
                    FileUtils.write(final_log_output, log_data, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
