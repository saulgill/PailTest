package com.mycompany.pailtest;

import com.backtype.hadoop.pail.Pail;
import com.backtype.hadoop.pail.Pail.TypedRecordOutputStream;
import com.backtype.hadoop.pail.PailSpec;
import com.backtype.hadoop.pail.SequenceFileFormat;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.slf4j.impl.StaticLoggerBinder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        try {
//            App.simpleIO();
//        } catch (IOException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            App.writeLogins();
//            App.readLogins();
//            App.appendData();
//            App.partitionData();
//              App.createCompressedPail();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void simpleIO() throws IOException 
    {
        Pail pail = Pail.create("C:/Users/Saul/Desktop/tmp/mypail");
        TypedRecordOutputStream os = pail.openWrite();
        os.writeObject(new byte[] {1, 2, 3});
        os.writeObject(new byte[] {1, 2, 3, 4});
        os.writeObject(new byte[] {1, 2, 3, 4, 5});
        os.close();
    }
    public static void writeLogins() throws IOException 
    {
        Pail<Login> loginPail = Pail.create("C:/Users/Saul/Desktop/tmp4/logins", new LoginPailStructure());
        TypedRecordOutputStream out = loginPail.openWrite();
        out.writeObject(new Login("johndoe", 1352680100));
        out.writeObject(new Login("janedoe", 1352675555));
        out.close();
    }
    public static void readLogins() throws IOException 
    {
        Pail<Login> loginPail = new Pail<Login>("C:/Users/Saul/Desktop/tmp1/logins");
        for(Login l : loginPail) 
        {
            System.out.println(l.userName + " " + l.loginUnixTime);
        }
    }
    public static void appendData() throws IOException 
    {
        Pail<Login> loginPail = new Pail<Login>("C:/Users/Saul/Desktop/tmp1/logins");
        Pail<Login> updatePail = new Pail<Login>("C:/Users/Saul/Desktop/tmp1/updates");
        loginPail.absorb(updatePail);
    }
    public static void partitionData() throws IOException 
    {
        Pail<Login> pail = Pail.create("C:/Users/Saul/Desktop/tmp2/partitioned_logins", new PartitionedLoginPailStructure());
        TypedRecordOutputStream os = pail.openWrite();
        os.writeObject(new Login("chris", 1352702020));
        os.writeObject(new Login("david", 1352788472));
        os.close();
    } 
    public static void createCompressedPail() throws IOException 
    {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(SequenceFileFormat.CODEC_ARG,
        SequenceFileFormat.CODEC_ARG_GZIP);
        options.put(SequenceFileFormat.TYPE_ARG,
        SequenceFileFormat.TYPE_ARG_BLOCK);
        LoginPailStructure struct = new LoginPailStructure();
        Pail compressed = Pail.create("C:/Users/Saul/Desktop/tmp3/compressed", new PailSpec("SequenceFile", options, struct));
    }
}
