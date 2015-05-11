package org.rjung.util.jenkins;

import org.rjung.util.jenkins.api.Job;
import org.rjung.util.jenkins.api.Overview;
import org.rjung.util.jenkins.config.Config;
import org.rjung.util.jenkins.config.Point;
import org.rjung.util.launchpad.midi.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Launchpad {

    private static final String JENKINS_JSON_API_PATH = "/api/json";
    private ObjectMapper mapper;
    private Config config;
    private org.rjung.util.launchpad.Launchpad launchpad;

    public static void main(String[] args) throws Exception {
        new Launchpad().run(args);
    }

    public void run(String... args) throws Exception {
        config = loadConfiguration(args.length > 0 ? args[0] : "launchpad.json");
        while (true) {
            Overview overview = getOverviewFromJenkins();
            for (Job job : overview.getJobs()) {
                if (config.hasJob(job.getName())) {
                    for (Point point : config.getJob(job.getName()).getPoints()) {
                        getLaunchpad().set(point.getX(), point.getY(),
                                getColor(job.getColor()));
                    }
                }
            }
            Thread.sleep(config.getSleep());
        }
    }

    private Color getColor(String color) {
        switch (color) {
        case "red":
            return Color.RED;
        case "blue":
            return Color.GREEN;
        case "yellow":
            return Color.YELLOW;
        case "disabled":
            return Color.OFF;
        default:
            return Color.OFF;
        }
    }

    private Overview getOverviewFromJenkins() throws IOException,
            JsonParseException, JsonMappingException {
        URL url = new URL(config.getJenkinsUrl() + JENKINS_JSON_API_PATH);
        URLConnection connection = url.openConnection();
        return getObjectMapper().readValue(connection.getInputStream(),
                Overview.class);
    }

    private Config loadConfiguration(String configFile)
            throws JsonParseException, IOException {
        return getObjectMapper().readValue(new File(configFile), Config.class);
    }

    private ObjectMapper getObjectMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false);
        }
        return mapper;
    }

    private org.rjung.util.launchpad.Launchpad getLaunchpad()
            throws FileNotFoundException {
        if (launchpad == null) {
            launchpad = new org.rjung.util.launchpad.Launchpad(
                    config.getMidiDevice());
        }
        return launchpad;
    }

}
