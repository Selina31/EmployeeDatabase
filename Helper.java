import java.util.List;

/**
 * An interface to help print out the arrayList
 *
 * <p>
 * Bugs: none
 *
 * @author Xuetong Du
 */
public class Helper {

	public static String printStringList(List<String> inputList) {
		StringBuffer buf = new StringBuffer();
		if (inputList != null && inputList.size() > 0) {
			for (String elt : inputList) {
				if (buf.length() == 0) {
					buf.append(elt);
				} else {
					buf.append(",");
					buf.append(elt);
				}
			}
		}
		return buf.toString();
	}
}
