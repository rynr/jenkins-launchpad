package org.rjung.util.jenkins.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Config {
    @JsonProperty("jenkins-url")
    private String jenkinsUrl;
    @JsonProperty("midi-device")
    private String midiDevice;
    private int sleep;
    private List<Job> jobs;
    private Map<String, Job> lookup;

    public Config() {
        lookup = new HashMap<String, Job>();
    }

    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public void setJenkinsUrl(String jenkinsUrl) {
        this.jenkinsUrl = jenkinsUrl;
    }

    public String getMidiDevice() {
        return midiDevice;
    }

    public void setMidiDevice(String midiDevice) {
        this.midiDevice = midiDevice;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.lookup.clear();
        for (Job job : jobs) {
            lookup.put(job.getName(), job);
        }
        this.jobs = jobs;
    }

    public boolean hasJob(String job) {
        return lookup.containsKey(job);
    }

    public Job getJob(String job) {
        return lookup.get(job);
    }

    @Override
    public String toString() {
        return "Config [jenkinsUrl=" + jenkinsUrl + ", midiDevice="
                + midiDevice + ", sleep=" + sleep + ", jobs=" + jobs
                + ", lookup=" + lookup + "]";
    }

}
