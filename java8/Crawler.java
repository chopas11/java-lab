import java.net.*;

public class Crawler {

	// список для хранения URL адресов + уровень этих адресов
	private URLPool pool;

    // Число потоков
    public int numThreads = 4;

    /**
     * Конструктор Crawler
     */
    public Crawler(String root, int max) throws MalformedURLException {
		//
		pool = new URLPool(max);
		//
		URL rootURL = new URL(root);
		// Добавляем исходный url в список
		pool.add(new URLDepthPair(rootURL, 0));
    }

    /**
     * Создание CrawlerTask потоков для поиска ссылок
     */
    public void crawl() {
	for (int i = 0; i < numThreads; i++) {
		// Вызов CrawlerTask
	    CrawlerTask crawler = new CrawlerTask(pool);
		// Создание потоков
	    Thread thread = new Thread(crawler);
	    thread.start();
	}
	//
	while (pool.getWaitCount() != numThreads) {
	    try {
		Thread.sleep(500);
	    }
	    catch (InterruptedException e) {
		System.out.println("Ignoring unexpected InterruptedException - " +
				   e.getMessage());
	    }
	}
	pool.printURLs();
    }

    /**
     * Основной метод
     */
    public static void main(String[] args) {
	// Вывести помощь при неправильном написании аргументов
	if (args.length < 2 || args.length > 5) {
	    System.err.println("Usage: java Crawler <URL> <depth> " +
			       "<patience> -t <threads>");
	    System.exit(1);
	}
	// Блок try catch
	try {
		// Вызов экземпляра
	    Crawler crawler = new Crawler(args[0], Integer.parseInt(args[1]));
		// Проход по аргументам
	    switch (args.length) {
			case 3:
				CrawlerTask.maxPatience = Integer.parseInt(args[2]);
				break;
			case 4:
				crawler.numThreads = Integer.parseInt(args[3]);
				break;
			case 5:
				CrawlerTask.maxPatience = Integer.parseInt(args[2]);
				crawler.numThreads = Integer.parseInt(args[4]);
				break;
	    }
		// Вызываем метод для генерации потоков
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
