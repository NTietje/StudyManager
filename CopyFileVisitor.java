import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFileVisitor extends SimpleFileVisitor<Path>{
		private Path target;
		private Path source;
		
		public CopyFileVisitor (Path source, Path target){
			this.target = target;
			this.source = source;
		}
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)throws IOException {
			Files.createDirectory(target.resolve(source.relativize(dir)));
			return FileVisitResult.CONTINUE;
		}
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			Files.delete(dir);
			return FileVisitResult.CONTINUE;
		}
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
			Files.move(file, target.resolve(source.relativize(file)));
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) {
			System.out.println(file.getFileName() + " konnte nicht kopiert werden.");
			return FileVisitResult.CONTINUE;
		}
		
	}

