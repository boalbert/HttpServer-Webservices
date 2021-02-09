import se.iths.plugin.DatabaseIMPL;
import se.iths.plugin.FileIMPL;
import se.iths.spi.IOhandler;

module se.iths.plugin {
    exports se.iths.plugin;
    requires se.iths.spi;
    requires java.persistence;
    provides IOhandler with DatabaseIMPL, FileIMPL;
}