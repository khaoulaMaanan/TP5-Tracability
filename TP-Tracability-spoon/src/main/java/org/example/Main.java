package org.example;


import processors.*;
import profiles.ProfileProcessor;
import spoon.Launcher;
import spoon.compiler.Environment;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Launcher launcher = new Launcher();
        launcher.addInputResource("C:\\Users\\khaou\\M2-GL\\Evolution-Restruturation-des-logiciels\\TP5\\TP-Tracability - Copie");

        Environment environment = launcher.getEnvironment();
        environment.setNoClasspath(true);
        environment.setComplianceLevel(11);
        environment.setCommentEnabled(true);
        environment.setAutoImports(true);

        launcher.addProcessor(new ClassProcessor());
        launcher.addProcessor(new ReturnProcessor());
        launcher.addProcessor(new ThrowProcessor());
        launcher.addProcessor(new CatchProcessor());
        launcher.addProcessor(new ForProcessor());
        launcher.addProcessor(new TryProcessor());
        ProfileProcessor.fromXmlToJson();
        ProfileProcessor.getRecords();


//        launcher.addProcessor(new ReturnProcessor());

        System.err.println("Start of transformation ...");
        launcher.run();
        System.err.println("... End of transformation.");
    }




}