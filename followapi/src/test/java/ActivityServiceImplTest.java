import com.example.models.SearchCriteria;
import com.example.models.elasticsearch.Activity;
import com.example.repository.base.ActivityRepository;
import com.example.service.ActivityServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ActivityServiceImplTest {

    @Mock
    ActivityRepository activityRepository;

    @InjectMocks
    ActivityServiceImpl activityService;

    @Test
    public void addActivityShouldReturnTrue(){
        Activity activity = new Activity();
        Date date = new Date();

        activity.setDate(date);
        activity.setEvent("click");
        activity.setIpAddress("0.0.0.0.0.0.0.1");
        activity.setUsername("aaa");

        Mockito.when(activityRepository.add(activity)).thenReturn(true);

        activityService.addActivity(activity);

        Assert.assertEquals(activityService.addActivity(activity), true);
    }

    @Test
    public void findByCriteriaShouldReturnFullMatch() throws Exception{
        LocalDate localDate = null;
        String str = "a b";
        SearchCriteria searchCriteria = new SearchCriteria(localDate, localDate, str, 1,1);

        Activity activity = new Activity();
        Date date = new Date();

        activity.setDate(date);
        activity.setEvent("a b");
        activity.setIpAddress("0.0.0.0.0.0.0.1");
        activity.setUsername("aaa");

        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(activity);

        Mockito.when(activityRepository.findFull(localDate, localDate, str, 1,1)).thenReturn(activities);

        activityService.findByCriteria(searchCriteria);

        Assert.assertEquals(1,activities.size());
        Assert.assertEquals(searchCriteria.getSearch(), activities.get(0).getEvent());
        Mockito.verify(activityRepository, Mockito.atLeast(1)).findFull(localDate, localDate, str, 1,1);
    }

    @Test
    public void GetActivityFirstSearchType_ReturnCorrectCount() throws Exception{

        Mockito.when(activityRepository.findFull(LocalDate.now(), LocalDate.now(), "login click logout", 1,1))
                .thenReturn(new ArrayList<Activity>() {{
            add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1"));
            add(new Activity(UUID.randomUUID().toString(), "click", "username" , new Date(), "121.0.0.1"));
            add(new Activity(UUID.randomUUID().toString(), "logout", "username" , new Date(), "121.0.0.1"));
        }});

        SearchCriteria criteria = new SearchCriteria(LocalDate.now(), LocalDate.now(), "login click logout", 1,1);

        List<Activity> result = activityService.findByCriteria(criteria);

        Assert.assertEquals(3, result.size());
    }

    @Test
    public void GetActivitySecondSearchType_ReturnCorrectCount() throws Exception{

        Mockito.when(activityRepository.findPerfect(LocalDate.now(), LocalDate.now(), "\"login\"", 1,1))
                .thenReturn(new ArrayList<Activity>() {{
                    add(new Activity(UUID.randomUUID().toString(), "login","username" , new Date(), "121.0.0.1"));
                    add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1"));
                    add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1"));
                }});

        SearchCriteria criteria = new SearchCriteria(LocalDate.now(), LocalDate.now(), "\"login\"", 1,1);

        List<Activity> result = activityService.findByCriteria(criteria);

        Assert.assertEquals(3, result.size());
    }

    @Test
    public void GetActivityThirdSearchType_ReturnCorrectCount() throws Exception{

        Mockito.when(activityRepository.findFullKeyValue(LocalDate.now(), LocalDate.now(), "event", "login", 1,1))
                .thenReturn(new ArrayList<Activity>() {{
                    add(new Activity(UUID.randomUUID().toString(), "login","username" , new Date(), "121.0.0.1"));
                    add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1"));
                    add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1"));
                }});

        SearchCriteria criteria = new SearchCriteria(LocalDate.now(), LocalDate.now(), "event:login", 1,1);

        List<Activity> result = activityService.findByCriteria(criteria);

        Assert.assertEquals(3, result.size());
    }

    @Test
    public void GetActivityFourthSearchType_ReturnCorrectCount() throws Exception{

        Mockito.when(activityRepository.findPerfectKeyValue(LocalDate.now(), LocalDate.now(), "event", "login", 1,1))
                .thenReturn(new ArrayList<Activity>() {{
                    add(new Activity(UUID.randomUUID().toString(), "login","username" , new Date(), "121.0.0.1"));
                    add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1"));
                    add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1"));
                }});

        SearchCriteria criteria = new SearchCriteria(LocalDate.now(), LocalDate.now(), "event=login", 1,1);

        List<Activity> result = activityService.findByCriteria(criteria);

        Assert.assertEquals(3, result.size());
    }

    @Test
    public void findByCriteriaShouldRunFindPerfectOnce() throws Exception{
        LocalDate localDate = null;
        String str = "\"a b\"";
        SearchCriteria searchCriteria = new SearchCriteria(localDate, localDate, str, 1,1);

        Activity activity = new Activity();
        Date date = new Date();

        activity.setDate(date);
        activity.setEvent("a b");
        activity.setIpAddress("0.0.0.0.0.0.0.1");
        activity.setUsername("aaa");

        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(activity);

        Mockito.when(activityRepository.findPerfect(localDate, localDate, str, 1,1)).thenReturn(activities);

        activityService.findByCriteria(searchCriteria);

        Mockito.verify(activityRepository, Mockito.atLeast(1)).findPerfect(localDate, localDate, str, 1,1);
    }

    @Test
    public void findByCriteriaShouldRunFindFullKeyValueOnce() throws Exception{
        LocalDate localDate = null;
        String str = "event:a";
        SearchCriteria searchCriteria = new SearchCriteria(localDate, localDate, str, 1,1);

        Activity activity = new Activity();
        Date date = new Date();

        activity.setDate(date);
        activity.setEvent("a");
        activity.setIpAddress("0.0.0.0.0.0.0.1");
        activity.setUsername("aaa");

        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(activity);

        Mockito.when(activityRepository.findFullKeyValue(localDate, localDate, "event", "a", 1,1)).thenReturn(activities);

        activityService.findByCriteria(searchCriteria);

        Mockito.verify(activityRepository, Mockito.atLeast(1)).findFullKeyValue(localDate, localDate, "event", "a", 1,1);
    }

    @Test
    public void findByCriteriaShouldRunFindPerfectKeyValueOnce() throws Exception{
        LocalDate localDate = null;
        String str = "event=a";
        SearchCriteria searchCriteria = new SearchCriteria(localDate, localDate, str, 1,1);

        Activity activity = new Activity();
        Date date = new Date();

        activity.setDate(date);
        activity.setEvent("a");
        activity.setIpAddress("0.0.0.0.0.0.0.1");
        activity.setUsername("aaa");

        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(activity);

        Mockito.when(activityRepository.findPerfectKeyValue(localDate, localDate, "event", "a", 1,1)).thenReturn(activities);

        activityService.findByCriteria(searchCriteria);

        Mockito.verify(activityRepository, Mockito.atLeast(1)).findPerfectKeyValue(localDate, localDate, "event", "a", 1,1);
    }
}
