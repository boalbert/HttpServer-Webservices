import se.iths.plugin.DatabaseIMPL;
import se.iths.plugin.FileIMPL;
import se.iths.spi.IOhandler;

module se.iths.plugin {
    exports se.iths.plugin;
    requires se.iths.spi;
    requires java.persistence;
    requires java.sql;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;
    opens se.iths.plugin.model to org.hibernate.orm.core;

    provides IOhandler with DatabaseIMPL, FileIMPL;
}