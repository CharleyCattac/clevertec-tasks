package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.Util;
import by.clevertec.util.date.DateUtil;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;

import java.awt.font.NumericShaper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19();
        task20();
        task21();
        task22();
    }

    public static List<Animal> task1() {
        List<Animal> animals = Util.getAnimals();
        int minAge = 10;
        int maxAge = 20;
        Range<Integer> ageRange = Range.between(minAge, maxAge);
        AtomicInteger animalCounter = new AtomicInteger();
        int chunkPerZoo = 7;
        int myZooNumber = 3;
        return animals.stream()
                .filter(animal -> ageRange.contains(animal.getAge()))
                .sorted(Comparator.comparingInt(Animal::getAge))
                .collect(Collectors.groupingBy(animal -> animalCounter.getAndIncrement() / chunkPerZoo))
                .get(myZooNumber - 1)
                .stream()
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public static List<String> task2() {
        List<Animal> animals = Util.getAnimals();
        String originJapan = "Japanese";
        String genderFemale = "Female";
        return animals.stream()
                .filter(animal -> animal.getOrigin().equalsIgnoreCase(originJapan))
                .map(animal -> {
                    if (animal.getGender().equalsIgnoreCase(genderFemale)) {
                        animal.setBread(animal.getBread().toUpperCase());
                    }
                    return animal.getBread();
                })
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public static List<String> task3() {
        List<Animal> animals = Util.getAnimals();
        int minAge = 30;
        return animals.stream()
                .filter(animal -> animal.getAge() >= minAge)
                .map(Animal::getOrigin)
                .distinct()
                .filter(origin -> origin.startsWith("A"))
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public static long task4() {
        List<Animal> animals = Util.getAnimals();
        String genderFemale = "Female";
        return animals.stream()
                .filter(animal -> animal.getGender().equalsIgnoreCase(genderFemale))
                .count();
    }

    public static boolean task5() {
        List<Animal> animals = Util.getAnimals();
        String originHungarian = "Hungarian";
        return animals.stream()
                    .anyMatch(animal -> animal.getOrigin().equalsIgnoreCase(originHungarian));
    }

    public static boolean task6() {
        List<Animal> animals = Util.getAnimals();
        String genderFemale = "Female";
        String genderMale = "Male";
        return animals.stream()
                    .allMatch(animal -> animal.getGender().equalsIgnoreCase(genderFemale)
                                        || animal.getGender().equalsIgnoreCase(genderMale));
    }

    public static boolean task7() {
        List<Animal> animals = Util.getAnimals();
        String originOceania = "Oceania";
        return animals.stream()
                    .noneMatch(animal -> animal.getOrigin().equalsIgnoreCase(originOceania));
    }

    public static int task8() {
        List<Animal> animals = Util.getAnimals();
        int limitSize = 100;
        return animals.stream()
                    .sorted(Comparator.comparing(Animal::getBread))
                    .limit(limitSize)
                    .max(Comparator.comparing(Animal::getAge))
                    .orElseThrow()
                    .getAge();
    }

    public static int task9() {
        List<Animal> animals = Util.getAnimals();
        return animals.stream()
                    .map(Animal::getBread)
                    .map(String::toCharArray)
                    .min(Comparator.comparingInt(charArr -> charArr.length))
                    .orElseThrow()
                    .length;
    }

    public static int task10() {
        List<Animal> animals = Util.getAnimals();
        return animals.stream()
                    .map(Animal::getAge)
                    .reduce(0, Integer::sum);
    }

    public static double task11() {
        List<Animal> animals = Util.getAnimals();
        String originIndonesian = "Indonesian";
        return animals.stream()
                    .filter(animal -> animal.getOrigin().equalsIgnoreCase(originIndonesian))
                    .mapToInt(Animal::getAge)
                    .average()
                    .orElseThrow();
    }

    public static List<Person> task12() {
        List<Person> persons = Util.getPersons();
        int minAge = 18;
        int maxAge = 27;
        int quote = 200;
        return persons.stream()
                .filter(person -> DateUtil.countYears(person.getDateOfBirth()) >= minAge
                        && DateUtil.countYears(person.getDateOfBirth()) <= maxAge)
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(quote)
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public static List<Person> task13() {
        List<House> houses = Util.getHouses();
        String houseHospital = "Hospital";
        int legalAge = 18;
        int seniorAge = 60;
        int quote = 500;
        Range<Integer> range = Range.between(legalAge, seniorAge);
        return houses.stream()
                .collect(Collectors.partitioningBy(house -> house.getBuildingType().equalsIgnoreCase(houseHospital)))
                .entrySet()
                .stream()
                .map(partitionEntry -> Map.entry(
                        partitionEntry.getValue().stream()
                                .map(House::getBuildingType)
                                .collect(Collectors.toSet()),
                        partitionEntry.getValue().stream()
                            .flatMap(house -> house.getPersonList().stream())
                            .sorted((person1, person2) -> {
                                int person1Age = DateUtil.countYears(person1.getDateOfBirth());
                                int person2Age = DateUtil.countYears(person2.getDateOfBirth());
                            if (range.contains(person1Age) && range.contains(person2Age)) {
                                return 0;
                            } else if (range.contains(person1Age)) {
                                return 1;
                            } else {
                                return -1;
                            }
                            })
                            .collect(Collectors.toList()))
                )
                .sorted(Comparator.comparing(entry -> !entry.getKey().contains(houseHospital)))
                .flatMap(sortedEntry -> sortedEntry.getValue().stream())
                .limit(quote)
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public static double task14() {
        List<Car> cars = Util.getCars();
        List<String> echelons = Arrays.asList("Turkmenistan", "Uzbekistan", "Kazakhstan", "Kyrgyzstan", "Russia", "Mongolia");
        double pricePerTon = 7.14;
        return cars.stream().parallel()
            .collect(Collectors.groupingBy(car -> {
                if (car.getCarMake().equals("Jaguar")
                        || car.getColor().equals("White")) {
                    return echelons.get(0);
                } else if (car.getMass() < 1500
                        && (car.getCarMake().equals("BMW") || car.getCarMake().equals("Lexus")
                        || car.getCarMake().equals("Chrysler") || car.getCarMake().equals("Toyota"))) {
                    return echelons.get(1);
                } else if ((car.getColor().equals("Black") && car.getMass() >= 4000)
                        || car.getCarMake().equals("GMC") || car.getCarMake().equals("Dodge")) {
                    return echelons.get(2);
                } else if (car.getReleaseYear() < 1982
                        || car.getCarMake().equals("Civic") || car.getCarMake().equals("Cherokee")) {
                    return echelons.get(3);
                } else if (car.getPrice() >= 40000
                        || !(car.getColor().equals("Yellow") || car.getColor().equals("Red")
                        || car.getColor().equals("Green") || car.getColor().equals("Blue"))) {
                    return echelons.get(4);
                } else if (car.getVin().contains("59")) {
                    return echelons.get(5);
                } else {
                    return "No country";
                }
            }))
            .entrySet()
            .stream()
            .peek(System.out::println)
            .map(entry -> Map.entry(entry.getKey(), entry.getValue().stream()
                    .mapToInt(Car::getPrice).sum() * pricePerTon))
            .takeWhile(entry -> echelons.contains(entry.getKey()))
            .peek(System.out::println)
            .mapToDouble(Map.Entry::getValue)
            .sum();
    }

    public static double task15() {
        List<Flower> flowers = Util.getFlowers();
        List<String> vaseMaterials = Arrays.asList("Glass", "Aluminum", "Steel");
        double waterCubePrice = 1.39;
        int daysPerYear = 365;
        return flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed())
                .sorted(Comparator.comparing(Flower::isShadePreferred).reversed())
                .filter(flower -> flower.getCommonName().charAt(0) >= 'S' || flower.getCommonName().charAt(0) <= 'C')
                .filter(flower -> flower.isShadePreferred()
                        && flower.getFlowerVaseMaterial().stream().anyMatch(vaseMaterials::contains))
                .mapToDouble(flower -> flower.getPrice() + flower.getWaterConsumptionPerDay() * waterCubePrice * daysPerYear)
                .sum();
    }

    public static List<Map.Entry<String, Integer>> task16() {
        List<Student> students = Util.getStudents();
        int legalAge = 18;
        return students.stream()
                .filter(student -> student.getAge() <= legalAge)
                .sorted(Comparator.comparing(Student::getSurname))
                .map(student -> Map.entry(student.getSurname(), student.getAge()))
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public static List<String> task17() {
        List<Student> students = Util.getStudents();
        return students.stream()
                .map(Student::getGroup)
                .distinct()
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public static List<Map.Entry<String, Double>> task18() {
        List<Student> students = Util.getStudents();
        return students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty))
                .entrySet()
                .stream()
                .map(groupedEntry -> Map.entry(groupedEntry.getKey(), groupedEntry.getValue().stream()
                        .mapToInt(Student::getAge)
                        .average()
                        .orElseThrow()))
                .sorted(Map.Entry.comparingByValue())
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public static List<String> task19(String... givenGroupName) {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        String groupName = givenGroupName[0];
        Predicate<Examination> thirdExamIsPassed = exam -> exam.getExam3() >= 4;
        return students.stream()
                .filter(student -> student.getGroup().equals(groupName))
                .filter(student -> examinations.stream()
                        .filter(exam -> exam.getStudentId() == student.getId())
                        .anyMatch(thirdExamIsPassed))
                .map(Student::getSurname)
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public static Map.Entry<String, Integer> task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        return students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty))
                .entrySet()
                .stream()
                .map(facultyEntry -> Map.entry(facultyEntry.getKey(),
                        facultyEntry.getValue().stream()
                                .filter(student -> examinations.stream()
                                        .anyMatch(exam -> exam.getStudentId() == student.getId()))
                                .map(student -> examinations.stream()
                                        .filter(exam -> exam.getStudentId() == student.getId())
                                        .mapToInt(Examination::getExam1)
                                        .findFirst()
                                        .orElseThrow())
                                .reduce(0, Integer::max)))
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow();
    }

    public static Map<String, Integer> task21() {
        List<Student> students = Util.getStudents();
        return students.stream()
                .collect(Collectors.groupingBy(Student::getGroup))
                .entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<String, Integer> task22() {
        List<Student> students = Util.getStudents();
        return students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty))
                .entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(),
                                entry.getValue()
                                        .stream()
                                        .min(Comparator.comparingInt(Student::getAge))
                                        .orElseThrow()
                                        .getAge()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
