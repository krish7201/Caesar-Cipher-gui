import java.io.File;

public class test {
    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        // Creates an array in which we will store the names of files and directories
        String[] pathnames;

        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File(System.getProperty("user.dir"));

        // Populates the array with names of files and directories
        pathnames = f.list();

        // For each pathname in the pathnames array
        assert pathnames != null;
        for (String pathname : pathnames) {
            // Print the names of files and directories
            System.out.println(pathname);
        }
    }
}
