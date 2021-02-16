import se.iths.plugin.GetContact;
import se.iths.plugin.GetFile;
import se.iths.plugin.GetContactInsert;
import se.iths.plugin.PostContact;
import se.iths.spi.IoHandler;

module se.iths.plugin {
    exports se.iths.plugin;
    requires se.iths.spi;
    requires java.persistence;
    requires java.sql;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;
    requires com.google.gson;
    opens se.iths.plugin.model to org.hibernate.orm.core, com.google.gson;

    provides IoHandler with GetContact, GetFile, PostContact, GetContactInsert;
}