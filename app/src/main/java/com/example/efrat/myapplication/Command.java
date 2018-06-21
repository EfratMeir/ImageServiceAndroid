package com.example.efrat.myapplication;

public class Command {

        public int CommandID;    // The Command ID
        public String[] Args;
        public String RequestDirPath;  // The Request Directory

        public Command(int id, String[] args, String path)
        {
            CommandID = id;
            Args = args;
            RequestDirPath = path;
        }

}
