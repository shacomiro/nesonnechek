package com.shacomiro.makeabook.ebook.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.shacomiro.makeabook.ebook.error.FileIOException;

public class IOUtil {
	private IOUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static List<String> readStringFromFile(Path path, Charset charset) {
		try {
			return Files.readAllLines(path, charset);
		} catch (IOException e) {
			throw new FileIOException("Fail to read string from file", e);
		}
	}

	public static Path createTempFile(Path path, String extension) {
		try {
			return Files.createTempFile(path, "temp_", extension);
		} catch (IOException e) {
			throw new FileIOException("Fail to create temporary file", e);
		}
	}

	public static InputStream loadFileToInputStream(Path path) {
		try {
			return Files.newInputStream(path);
		} catch (IOException e) {
			throw new FileIOException("Fail to load InputStream of file '" + path.getFileName() + "'", e);
		}
	}

	public static OutputStream loadFileToOutputStream(Path path) {
		try {
			return Files.newOutputStream(path);
		} catch (IOException e) {
			throw new FileIOException("Fail to load OutputStream of file '" + path.getFileName() + "'", e);
		}
	}

	public static void createDirectory(Path path) {
		if (!Files.exists(path)) {
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				throw new FileIOException(
						"Fail to create directory '" + path.toAbsolutePath().normalize().toString() + "'", e);
			}
		}
	}

	public static void deleteDirectory(Path path) {
		if (Files.exists(path)) {
			try {
				List<Path> dirListPath = Files.list(path).collect(Collectors.toList());

				if (!dirListPath.isEmpty()) {
					for (Path p : dirListPath) {
						if (Files.isDirectory(p)) {
							deleteDirectory(Paths.get(p.toString(), File.separatorChar + p.getFileName().toString()));
						} else {
							Files.delete(p);
						}
					}
				}
				Files.delete(path);
			} catch (IOException e) {
				throw new FileIOException("Fail to delete directory '" + path + "'", e);
			}
		}
	}

	public static void deleteFile(Path path) {
		try {
			Files.delete(path);
		} catch (IOException e) {
			throw new FileIOException("Fail to delete file '" + path.getFileName() + "'", e);
		}
	}
}
