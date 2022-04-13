import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.regex.*;

public class Crawler {

	// Списки для хранения URL адресов
	private LinkedList<URLDepthPair> pendingURLs;
	private LinkedList<URLDepthPair> processedURLs;
	// Регулярные выражения
	public static final String LINK_REGEX = "href\\s*=\\s*\"([^$^\"]*)\"";
	public static final Pattern LINK_PATTERN = Pattern.compile(LINK_REGEX, Pattern.CASE_INSENSITIVE);
	// Глубина поиска ссылок
	private int depth;

    /**
     * Конструктор Crawler
     */
    public Crawler(String root, int maxDepth) throws MalformedURLException {

		// Инициализация полей
		depth = maxDepth;
		pendingURLs = new LinkedList<URLDepthPair>();
		processedURLs = new LinkedList<URLDepthPair>();

		URL rootURL = new URL(root);

		URLDepthPair nextPair = new URLDepthPair(rootURL, 0);
		if (nextPair.getDepth() < depth) {
			pendingURLs.add(nextPair);
		}
		processedURLs.add(nextPair);

    }

	/**
	 * Метод для содания сокета и отправки HTTP апроса на страницу из nextPair
	 */
	public Socket sendRequest(URLDepthPair nextPair) throws UnknownHostException, SocketException, IOException {
		//Новый сокет
		Socket socket = new Socket(nextPair.getHost(), 80);
		socket.setSoTimeout(3000);

		OutputStream os = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(os, true);

		// Запрос
		writer.println("GET " + nextPair.getDocPath() + " HTTP/1.1");
		writer.println("Host: " + nextPair.getHost());
		writer.println("Connection: close");
		writer.println();

		return socket;
	}

	/**
	 * Поиск всех ссылок на странице и добавление их в списки
	 */
	public void processURL(URLDepthPair url) throws IOException {
		Socket socket;
		try {
			socket = sendRequest(url);
		}
		catch (UnknownHostException e) {
			System.err.println("Host "+ url.getHost() + " couldn't be determined");
			return;
		}
		catch (SocketException e) {
			System.err.println("Error with socket connection: " + url.getURL() +
					" - " + e.getMessage());
			return;
		}
		catch (IOException e) {
			System.err.println("Couldn't retrieve page at " + url.getURL() +
					" - " + e.getMessage());
			return;
		}

		InputStream input = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));

		String line;
		while ((line = reader.readLine()) != null) {
			Matcher LinkFinder = LINK_PATTERN.matcher(line);
			while (LinkFinder.find()) {
				String newURL = LinkFinder.group(1);

				URL newSite;
				try {
					if (URLDepthPair.isAbsolute(newURL)) {
						newSite = new URL(newURL);
					}
					else {
						newSite = new URL(url.getURL(), newURL);
					}
					URLDepthPair nextPair = new URLDepthPair(newSite, url.getDepth() + 1);
					if (nextPair.getDepth() < depth) {
						pendingURLs.add(nextPair);
					}
					processedURLs.add(nextPair);
				}
				catch (MalformedURLException e) {
					System.err.println("Error with URL - " + e.getMessage());
				}
			}
		}
		reader.close();

		// Close the socket
		try {
			socket.close();
		}
		catch (IOException e) {
			System.err.println("Couldn't close connection to " + url.getHost() +
					" - " + e.getMessage());
		}
	}

    public void crawl() {
		while (!pendingURLs.isEmpty()) {
			URLDepthPair nextPair;
			nextPair = pendingURLs.removeFirst();
			try {
				processURL(nextPair);
			} catch (IOException e) {
				System.err.println("Error reading the page at " + nextPair.getURL() +
						" - " + e.getMessage());
			}
		}
		// Вывод числа найденных ссылок
		System.out.println("\nUnique URLs Found: " + processedURLs.size());
		// Вывод самих ссылок списком
		while (!processedURLs.isEmpty()) {
			System.out.println(processedURLs.removeFirst());
		}
    }

    /**
     * Основной метод Main
     */
    public static void main(String[] args) {
	// Вывести помощь при неправильном написании аргументов
	if (args.length < 2) {
	    System.err.println("Usage: java Crawler <URL> <depth> ");
	    System.exit(1);
	}
	try {
		// Вызов экземпляра класса
	    Crawler crawler = new Crawler(args[0], Integer.parseInt(args[1]));
	    crawler.crawl();
	}
	// Ошибка Url
	catch (MalformedURLException e) {
	    System.err.println("Error: The URL " + args[0] + " is not valid");
	    System.exit(1);
	}
	System.exit(0);
    }
}
