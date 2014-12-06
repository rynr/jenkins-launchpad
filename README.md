jenkins-launchpad
=================

Display the status of a Jenkins on a Launchpad.

This is the first running example to display the status of some Jenkisn Jobs on a Launchpad.  
It uses a external (Launchpad-Library)[https://rynr.github.io/launchpad].

It is configured with a json file that can be given as a argument on startup.

The json should have the following syntax (the syntax will change with the next updates):

```json
{
  "jenkins-url": "https://ci.jenkins-ci.org",
  "midi-device": "/dev/midi2",
  "sleep": 60000,
  "jobs": [
    {"name": "infra_accountapp",      "points": [{"x":0, "y":0}, {"x":0, "y":1}]},
    {"name": "core_selenium-test",    "points": [{"x":1, "y":0}, {"x":1, "y":1}]},
    {"name": "infra_main_svn_to_git", "points": [{"x":2, "y":0}, {"x":2, "y":1}]},
    {"name": "jenkins_rc_branch",     "points": [{"x":3, "y":0}, {"x":3, "y":1}]}
  ]
}
```

Troubleshooting
---------------

If you get a error like `Unexpected character ('<' (code 60)): expected a valid value (number, String, array, object, 'true', 'false' or 'null')`, then the URL of the jenkins is not correctly given, or the jenkin currently is failing. Potentially you specified `http://` instead of `https://`.
