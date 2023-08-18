package application;
import org.apache.commons.text.diff.CommandVisitor;
import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;

public class Diff {
	public static String performDiffAndHighlight(String storedHtml, String html) {
        StringsComparator comparator = new StringsComparator(storedHtml, html);
        EditScript<Character> script = comparator.getScript();

        StringBuilder diffHtml = new StringBuilder();
        diffHtml.append("<html><head><style>")
        .append("ins { background-color: #c6ffc6; }")
        .append("del { background-color: #ffcccc; }")
        .append("</style></head><body>");

        script.visit(new CommandVisitor<Character>() {
            @Override
            public void visitInsertCommand(Character c) {
                diffHtml.append("<ins>").append(c).append("</ins>");
            }
            @Override
            public void visitKeepCommand(Character c) {
                diffHtml.append(c);
            }
            @Override
            public void visitDeleteCommand(Character c) {
                diffHtml.append("<del>").append(c).append("</del>");
            }
            public void visitReplaceCommand(Character c1, Character c2) {
                diffHtml.append("<del>").append(c1).append("</del>")
                        .append("<ins>").append(c2).append("</ins>");
            }
        });
        diffHtml.append("</body></html>");
        return diffHtml.toString();
    }
}
