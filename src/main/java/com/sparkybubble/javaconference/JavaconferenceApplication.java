package com.sparkybubble.javaconference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class JavaconferenceApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(JavaconferenceApplication.class, args);
//	}

	public static void main(String[] args) {
		HashMap<String, Integer> file = readFile();
		TreeMap<String, Integer> sort = descOrder(file);
		List<List<String>> track = new ArrayList<>();
		List<String> slots = sortTalks(sort);

		//Figure out a real display
		for(String s : slots){
			System.out.println(s);
		}
	}

	public static HashMap<String, Integer> readFile(){
		HashMap<String, Integer> input = new HashMap<>();
		String talk; int duration;

		try {

			File file = new File("src/main/resources/input.txt");
			Scanner scan = new Scanner(file);

			while(scan.hasNextLine()) {

				String line = scan.nextLine();
				talk = line.substring(0, line.lastIndexOf(" "));

				if(line.contains("lightning")){
					duration = 5;
				} else {
//                    duration = Integer.parseInt(line.substring(line.lastIndexOf(" "), Integer.parseInt(line.substring(line.lastIndexOf(" ")+2))));
					duration = Integer.parseInt(line.substring(line.lastIndexOf(" ")+1, line.lastIndexOf(" ") + 3));
				}
				input.put(talk, duration);
			}
		} catch(Exception e) {
			System.out.println(e);
		}

		return input;
	}

	public static List<String> sortTalks(TreeMap<String, Integer> talks){
		List<String> removed = new ArrayList<>();
		List<String> sorted = new ArrayList<>();
		int morning = 180, afternoon = 240;
		sorted.add("MORNING");
		Iterator morningIt = talks.descendingMap().entrySet().iterator();

		//while loop for the morning slots
		while(morningIt.hasNext()){
			if(morning == 0){
				break;
			}
			Map.Entry<String,Integer> event = (Map.Entry<String, Integer>) morningIt.next();
			if(event.getValue() <= morning) {
				//if statement checks to see whether the talk being checked can fit into the slot
				sorted.add((String)event.getKey() + " " + (int)event.getValue() + "mins");
				morning -= (int)event.getValue();
				removed.add((String)event.getKey());
			}
		}

		talks.keySet().removeAll(removed);
		removed.clear();
		sorted.add("AFTERNOON");
		Iterator afternoonIt = talks.entrySet().iterator();

		//while loops for the afternoon slots
		while(afternoonIt.hasNext()){
			if(afternoon == 0){
				break;
			}
			Map.Entry<String, Integer> event = (Map.Entry<String, Integer>) afternoonIt.next();
			if(event.getValue() <= afternoon) {
				//if statement checks to see whether the talk being checked can fit into the slot
				sorted.add(event.getKey() + " " + Integer.toString(event.getValue()) + "mins");
				afternoon -= event.getValue();
				removed.add(event.getKey());
			}
		}

		talks.keySet().removeAll(removed);
		sorted.add("Networking Event");

		return sorted;
	}

	public static TreeMap<String, Integer> descOrder(HashMap<String, Integer> map) {
		return new TreeMap<>(map);
	}
}
