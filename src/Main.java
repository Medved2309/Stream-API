import education.Education;
import person.Person;
import sex.Sex;

import javax.swing.*;
import java.sql.ClientInfoStatus;
import java.util.stream.*;


import java.util.*;


public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );

        }
        long count = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + count);


        List<String> conscripts = persons.stream()
                .filter(person -> person.getAge() < 27)
                .filter(person -> person.getAge() > 18)
                .filter(person -> person.getSex() == Sex.MAN)
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Список фамилий призывников: ");
        conscripts.forEach(System.out::println);


        List<Person> workWoman = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getSex() == Sex.WOMAN)
                .filter(person -> person.getAge() > 18)
                .filter(person -> person.getAge() < 60)
                .collect(Collectors.toList());



        List<Person> workMan = persons.parallelStream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() > 18)
                .filter(person -> person.getAge() < 65)
                .collect(Collectors.toList());


        List<String> workPeople = Stream.of(workMan, workWoman)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::toString)
                .collect(Collectors.toList());
        workPeople.forEach(System.out::println);


    }
}