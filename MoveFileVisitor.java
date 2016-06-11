import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * The class follows a given file path to a file and moves the file to the destination specified in the constructor. If
 * the file is a directory, its contents are moved as well.
 * @author katharina
 */
public class MoveFileVisitor extends SimpleFileVisitor<Path> implements Serializable{

        static final long serialVersionUID = 1L;
		private Path target;
		private Path source;
		
		public MoveFileVisitor(Path source, Path target){
			this.target = target;
			this.source = source;
		}

    /**
     * creates the new directory before starting to move the contents of the old one
     * @param dir path to new directory
     * @param attrs
     * @return
     * @throws IOException
     */
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)throws IOException {
			Files.createDirectory(target.resolve(source.relativize(dir)));
			return FileVisitResult.CONTINUE;
		}

    /**
     * deletes directory after all its contents have been moved
     * @param dir
     * @param exc
     * @return
     * @throws IOException
     */
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			Files.delete(dir);
			return FileVisitResult.CONTINUE;
		}

    /**
     * moves file to its new destination
     * @param file
     * @param attrs
     * @return
     * @throws IOException
     */
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
			Files.move(file, target.resolve(source.relativize(file)));
			return FileVisitResult.CONTINUE;
		}

    /**
     * displays error message if file could not be visited
     * @param file
     * @param exc
     * @return
     */
		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) {
			System.out.println(file.getFileName() + " konnte nicht kopiert werden.");
			return FileVisitResult.CONTINUE;
		}
		
	}

