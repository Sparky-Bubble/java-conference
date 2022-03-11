package com.sparkybubble.javaconference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@SpringBootApplication
public class JavaconferenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaconferenceApplication.class, args);
		HashMap<String, Integer> file = readFile();
		ArrayList<ArrayList<String>> track = new ArrayList<>();

		while(!file.isEmpty()) {
			track.add(sortTalk(file));
		}

		//Figure out a real display
		int trackNum = 1;
		for(List<String> list : track){
			System.out.println("Track " + trackNum + ":");
			for(String s : list){
				System.out.println(s);
			}
			trackNum++;
		}
	}
	public static HashMap<String, Integer> readFile(){
		HashMap<String, Integer> input = new HashMap<>();
		String talk; int duration;
		try{
			File file = new File("src/main/resources/text.txt");
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				talk = line.substring(0, line.lastIndexOf(" "));
				if(line.contains("lightning")){
					duration = 5;
				}else {
					duration = Integer.parseInt(line.substring(line.lastIndexOf(" ")+1, line.lastIndexOf(" ") + 3));
				}
				input.put(talk, duration);
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return input;
	}
	public static ArrayList<String> sortTalk(HashMap<String, Integer> talks){
		Set<String> removed = new HashSet<>();
		ArrayList<String> sorted = new ArrayList<>();
		int morning = 180, afternoon = 240;

		LocalTime morningSlot = LocalTime.parse("09:00");
		LocalTime afternoonSlot = LocalTime.parse("13:00");
//        sorted.add("MORNING");
		Iterator morningIt = talks.entrySet().iterator();

		//while loop for the morning slots
		while(morningIt.hasNext()){

			if(morning == 0){
				break;
			}
			Map.Entry<String,Integer> event = (Map.Entry<String, Integer>) morningIt.next();
			if(event.getValue() <= morning) {
				//if statement checks to see whether the talk being checked can fit into the slot
				String time = morningSlot.format(
						DateTimeFormatter.ofLocalizedTime( FormatStyle.SHORT )
								.withLocale( Locale.US ));
				sorted.add(time + " " + event.getKey() + " " + event.getValue() + "mins");
				morningSlot = morningSlot.plusMinutes(event.getValue());
				morning -= event.getValue();
				removed.add((String)event.getKey());
			}
		}
		//removing the talks that have been already placed in the morning slot
		//this is so that the loop won't have any duplicates for the afternoon slot
		talks.entrySet().removeIf(entry-> removed.contains(entry.getKey()));
		removed.clear();

		sorted.add("12:00 PM LUNCH");
		Iterator afternoonIt = talks.entrySet().iterator();
		//while loops for the afternoon slots
		while(afternoonIt.hasNext()){
			if(afternoon == 0){
				break;
			}
			Map.Entry<String, Integer> event = (Map.Entry<String, Integer>) afternoonIt.next();
			if(event.getValue() <= afternoon) {
				//if statement checks to see whether the talk being checked can fit into the slot
				String time = afternoonSlot.format(
						DateTimeFormatter.ofLocalizedTime( FormatStyle.SHORT )
								.withLocale( Locale.US ));
				sorted.add(time + " " + event.getKey() + " " + Integer.toString(event.getValue()) + "mins");
				afternoonSlot = afternoonSlot.plusMinutes(event.getValue());
				afternoon -= event.getValue();
				removed.add(event.getKey());
			}
		}
		talks.entrySet().removeIf(entry-> removed.contains(entry.getKey()));
		sorted.add("5:00 PM NETWORKING EVENT\n");
		return sorted;
	}
}
