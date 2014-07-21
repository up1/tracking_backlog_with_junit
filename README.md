Backlog with JUnit
===========================

Mapping testcases in JUnit with Backlog Item



* How to use ?
 
  ```
  @RunWith(value=BacklogRunner.class)
  public class HelloTest {
  
    @Test
    @Backlog(name="FEATURE_0001")
    public void firstTest() {
    }
    
    @Test
    @Backlog(name="BUG_0001")
    public void secondTest() {
    }
    
    @Test
    @Backlog(name="TECHNICAL_WORK_0001")
    public void thridTest() {
    }
  
  }
  
  ```

* Result after run JUnit test cases
  
  Program will generate file backlog_result/result_<filename>.json
  And generate file result.html to summarize all json file
