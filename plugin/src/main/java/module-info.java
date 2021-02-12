import se.iths.plugin.DatebaseImpl;
import se.iths.plugin.FileImpl;
import se.iths.spi.IoHandler;

module se.iths.plugin {
    exports se.iths.plugin;
    requires se.iths.spi;
    requires java.persistence;
    requires java.sql;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;
    opens se.iths.plugin.model to org.hibernate.orm.core;

    provides IoHandler with DatebaseImpl, FileImpl;
}