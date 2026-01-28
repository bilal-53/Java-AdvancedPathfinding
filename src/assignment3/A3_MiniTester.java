package assignment3;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class A3_MiniTester {

    private static int proficiencyPassed = 0;
    private static int approachMasteryPassed = 0;
    private static int fullMasteryPassed = 0;

    @Test
    @DisplayName("Count number of tests passed")
    @Order(100)
    void testBasicLevelPassed() {
        System.out.println("Proficiency passed: " + proficiencyPassed );
        System.out.println("Approaching Mastery passed: " + approachMasteryPassed);
        System.out.println("Full Mastery passed: " + fullMasteryPassed);
    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test hire 1")
    @Tag("score:1")
    @Order(1)
    // Ensures that the order of seniority is correct and most furry is root with two cats
    public void hire_test1() {
        Cat A = new Cat("A", 40, 50, 2, 85.0);
        Cat B = new Cat("B", 20, 10, 2, 20.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);

        assertNotNull(cafe.root, "Root of the hierarchy should not be null");
        assertEquals(B, cafe.root.senior.catEmployee, "Seniority is not set correctly");
        assertEquals(A, cafe.root.catEmployee, "Root is not set correctly");

        approachMasteryPassed++;

    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test hire 2")
    @Tag("score:1")
    @Order(1)
    // test1 but monthHired reversed so seniority reversed
    public void hire_test2() {
        Cat A = new Cat("A", 45, 50, 5, 85.0);
        Cat B = new Cat("B", 65, 23, 2, 250.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);

        assertNotNull(cafe.root, "Root of the hierarchy should not be null");
        assertEquals(B, cafe.root.junior.catEmployee, "Seniority is not set correctly");
        assertEquals(A, cafe.root.catEmployee, "Root is not set correctly");

        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test hire 3")
    @Tag("score:1")
    @Order(1)
    // Ensures that the order of seniority is correct and most furry is root with three cats
    public void hire_test3() {
        Cat A = new Cat("A", 40, 50, 5, 85.0);
        Cat B = new Cat("B", 20, 40, 2, 250.0);
        Cat C = new Cat("C", 10, 30, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);

        assertNotNull(cafe.root, "Root of the hierarchy should not be null");
        assertEquals(B, cafe.root.senior.catEmployee, "Seniority is not set correctly");
        assertEquals(C, cafe.root.senior.senior.catEmployee, "Seniority is not set correctly");
        assertEquals(A, cafe.root.catEmployee, "Root is not set correctly");

        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test hire 4")
    @Tag("score:1")
    @Order(1)
    // Ensures that the order of seniority is correct and most furry is root with three cats with reversed seniority
    public void hire_test4() {
        Cat A = new Cat("A", 10, 30, 5, 85.0);
        Cat B = new Cat("B", 20, 20, 2, 250.0);
        Cat C = new Cat("C", 30, 10, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);

        assertNotNull(cafe.root, "Root of the hierarchy should not be null");
        assertEquals(B, cafe.root.junior.catEmployee, "Seniority is not set correctly");
        assertEquals(C, cafe.root.junior.junior.catEmployee, "Seniority is not set correctly");
        assertEquals(A, cafe.root.catEmployee, "Root is not set correctly");

        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test hire 5")
    @Tag("score:1")
    @Order(1)
    // Ensures that the order of seniority is correct and most furry is root with three cats with different order seniority
    public void hire_test5() {
        Cat A = new Cat("A", 30, 40, 5, 85.0);
        Cat B = new Cat("B", 20, 20, 2, 250.0);
        Cat C = new Cat("C", 10, 30, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);

        assertNotNull(cafe.root, "Root of the hierarchy should not be null");
        assertEquals(B, cafe.root.senior.junior.catEmployee, "Seniority is not set correctly");
        assertEquals(C, cafe.root.senior.catEmployee, "Seniority is not set correctly");
        assertEquals(A, cafe.root.catEmployee, "Root is not set correctly");

        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test retire 1")
    @Tag("score:1")
    @Order(1)
    // Tests that removing a junior cat leaves only the root in the tree
    public void retire_test1() {
        Cat A = new Cat("A", 30, 40, 5, 85.0);
        Cat B = new Cat("B", 20, 20, 2, 250.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.retire(B);

        assertNotNull(cafe.root, "Root of the hierarchy should not be null");
        assertEquals(A, cafe.root.catEmployee, "Root is not set correctly");
        assertNull(cafe.root.junior, "Only root should be remaining in the hierarchy after retire");
        assertNull(cafe.root.senior, "Only root should be remaining in the hierarchy after retire");


        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test retire 2")
    @Tag("score:1")
    @Order(1)
    // Tests that removing a senior cat leaves only the root in the tree with months hired reversed
    public void retire_test2() {
        Cat A = new Cat("A", 30, 40, 5, 85.0);
        Cat B = new Cat("B", 40, 20, 2, 250.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.retire(B);

        assertNotNull(cafe.root, "Root of the hierarchy should not be null");
        assertEquals(A, cafe.root.catEmployee, "Root is not set correctly");
        assertNull(cafe.root.junior, "Only root should be remaining in the hierarchy after retire");
        assertNull(cafe.root.senior, "Only root should be remaining in the hierarchy after retire");

        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test retire 3")
    @Tag("score:1")
    @Order(1)
    // Test1 but with two cats remaining
    public void retire_test3() {
        Cat A = new Cat("A", 30, 30, 5, 85.0);
        Cat B = new Cat("B", 20, 20, 2, 250.0);
        Cat C = new Cat("C", 10, 10, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);
        cafe.retire(B);

        assertNotNull(cafe.root, "Root of the hierarchy should not be null");
        assertEquals(A, cafe.root.catEmployee, "Root is not set correctly");
        assertNull(cafe.root.junior, "No junior should be remaining in the hierarchy after retire");
        assertEquals(C, cafe.root.senior.catEmployee, "One senior should be remaining in the hierarchy after retire");

        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test retire 4")
    @Tag("score:1")
    @Order(1)
    // Test1 but with two cats remaining
    public void retire_test4() {
        Cat A = new Cat("A", 10, 40, 5, 85.0);
        Cat B = new Cat("B", 20, 20, 2, 250.0);
        Cat C = new Cat("C", 30, 30, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);
        cafe.retire(B);

        assertNotNull(cafe.root, "Root of the hierarchy should not be null");
        assertEquals(A, cafe.root.catEmployee, "Root is not set correctly");
        assertNull(cafe.root.senior, "No senior should be remaining in the hierarchy after retire");
        assertEquals(C, cafe.root.junior.catEmployee, "One junior should be remaining in the hierarchy after retire");

        approachMasteryPassed++;
    }


    @Test
    @DisplayName("PROFICIENCY: Test findMostJunior 1")
    @Tag("score:1")
    @Order(1)
    // Ensures the correct identification of the most junior cat at the deepest level in the tree
    public void findMostJunior_test1() {
        Cat A = new Cat("A", 10, 40, 5, 85.0);
        Cat B = new Cat("B", 20, 30, 2, 250.0);
        Cat C = new Cat("C", 30, 20, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);

        assertEquals(C, cafe.root.findMostJunior(), "Test failed most junior cat is C but got " + cafe.root.findMostJunior().toString());

        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test findMostJunior 2")
    @Tag("score:1")
    @Order(1)
    // Ensures the correct identification of the most junior cat at the deepest level in the tree but month hired reversed
    public void findMostJunior_test2() {
        Cat A = new Cat("A", 30, 40, 5, 85.0);
        Cat B = new Cat("B", 20, 20, 2, 250.0);
        Cat C = new Cat("C", 10, 30, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);

        assertEquals(A, cafe.root.findMostJunior(), "Test failed most junior cat is A but got " + cafe.root.findMostJunior().toString());

        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test findMostJunior 3")
    @Tag("score:1")
    @Order(1)
    // Ensures the correct identification of the most junior cat at the deepest level in the tree but unbalanced tree
    public void findMostJunior_test3() {
        Cat A = new Cat("A", 40, 50, 5, 85.0);
        Cat B = new Cat("B", 30, 30, 2, 250.0);
        Cat C = new Cat("C", 60, 20, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);

        assertEquals(C, cafe.root.findMostJunior(), "Test failed most junior cat is C but got " + cafe.root.findMostJunior().toString());

        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test findMostSenior 1")
    @Tag("score:1")
    @Order(1)
    // Ensures the correct identification of the most senior cat at the deepest level in the tree
    public void findMostSenior_test1() {
        Cat A = new Cat("A", 40, 50, 5, 85.0);
        Cat B = new Cat("B", 30, 30, 2, 250.0);
        Cat C = new Cat("C", 20, 20, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);

        assertEquals(C, cafe.root.findMostSenior(), "Test failed most senior cat is C but got " + cafe.root.findMostJunior().toString());

        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test findMostSenior 2")
    @Tag("score:1")
    @Order(1)
    // Ensures the correct identification of the most senior cat at the deepest level in the tree but month hired reversed
    public void findMostSenior_test2() {
        Cat A = new Cat("A", 10, 50, 5, 85.0);
        Cat B = new Cat("B", 30, 30, 2, 250.0);
        Cat C = new Cat("C", 60, 20, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);

        assertEquals(A, cafe.root.findMostSenior(), "Test failed most senior cat is A but got " + cafe.root.findMostJunior().toString());

        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test findMostSenior 3")
    @Tag("score:1")
    @Order(1)
    // Ensures the correct identification of the most senior cat at the deepest level in the tree but unbalanced tree
    public void findMostSenior_test3() {
        Cat A = new Cat("A", 20, 50, 5, 85.0);
        Cat B = new Cat("B", 30, 30, 2, 250.0);
        Cat C = new Cat("C", 10, 20, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);

        assertEquals(C, cafe.root.findMostSenior(), "Test failed most senior cat is C but got " + cafe.root.findMostJunior().toString());

        proficiencyPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test build Hall of Fame 1")
    @Tag("score:1")
    @Order(1)
    // Check hall of fame when all cats in the café are included
    public void buildHOF_test1() {
        Cat A = new Cat("A", 20, 50, 5, 85.0);
        Cat B = new Cat("B", 30, 30, 2, 250.0);
        Cat C = new Cat("C", 10, 20, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);
        ArrayList<Cat> res = new ArrayList<>();
        res.add(A);
        res.add(B);
        res.add(C);

        ArrayList<Cat> actual = cafe.buildHallOfFame(3);

        for (int i = 0; i < 3; i += 1) {
            assertEquals(res.get(i), actual.get(i), "Test failed when building hall of fame");
        }
        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test build Hall of Fame 2")
    @Tag("score:1")
    @Order(1)
    // Check when hall of fame contains a subset of the cats in the café
    public void buildHOF_test2() {
        Cat A = new Cat("A", 20, 50, 5, 85.0);
        Cat B = new Cat("B", 30, 30, 2, 250.0);
        Cat C = new Cat("C", 10, 20, 12, 30.0);
        Cat D = new Cat("D", 5, 25, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);
        cafe.hire(D);
        ArrayList<Cat> res = new ArrayList<>();
        res.add(A);
        res.add(B);
        res.add(D);

        ArrayList<Cat> actual = cafe.buildHallOfFame(3);

        for (int i = 0; i < 3; i += 1) {
            assertEquals(res.get(i), actual.get(i), "Test failed when building hall of fame");
        }
        approachMasteryPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test budget grooming expenses 1")
    @Tag("score:1")
    @Order(1)
    // Calculates and verifies the total grooming expenses for all cats over a given number of weeks
    public void budgetGroomingExpenses_test() {
        Cat A = new Cat("A", 20, 50, 5, 85.0);
        Cat B = new Cat("B", 30, 30, 2, 250.0);
        Cat C = new Cat("C", 10, 20, 12, 30.0);
        Cat D = new Cat("D", 5, 25, 12, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);
        cafe.hire(D);

        float expected = 85 + 250 + 30 + 30;
        assertEquals(expected, cafe.budgetGroomingExpenses(13), "Test failed when calculating grooming expenses");

        proficiencyPassed++;
    }

    @Test
    @DisplayName("MASTERY: Test get grooming schedule 1")
    @Tag("score:1")
    @Order(1)
    // Calculates and verifies the total grooming expenses for all cats over a given number of weeks
    public void getGroomingSchedule_test() {
        Cat A = new Cat("A", 30, 50, 5, 85.0);
        Cat B = new Cat("B", 20, 30, 2, 250.0);
        Cat C = new Cat("C", 15, 20, 12, 30.0);
        Cat D = new Cat("D", 10, 25, 12, 30.0);
        Cat E = new Cat("E", 7, 60, 20, 30.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(A);
        cafe.hire(B);
        cafe.hire(C);
        cafe.hire(D);
        cafe.hire(E);

        ArrayList<ArrayList<Cat>> ans = new ArrayList<>();
        ArrayList<Cat> week0 = new ArrayList<>();
        ArrayList<Cat> week1 = new ArrayList<>();
        ArrayList<Cat> week2 = new ArrayList<>();

        week0.add(A);
        week0.add(B);
        week1.add(C);
        week1.add(D);
        week2.add(E);

        ans.add(week0);
        ans.add(week1);
        ans.add(week2);

        ArrayList<ArrayList<Cat>> res = cafe.getGroomingSchedule();

        assertEquals(res.size(), ans.size(), "Test failed when calculating grooming schedule as size is not equal");

        fullMasteryPassed++;
    }

}
