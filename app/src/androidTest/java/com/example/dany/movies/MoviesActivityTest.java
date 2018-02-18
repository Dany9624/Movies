package com.example.dany.movies;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.dany.movies.View.MoviesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import android.support.test.espresso.contrib.RecyclerViewActions;

/**
 * Created by Dany on 17.2.2018 г..
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MoviesActivityTest {

    private static final int ITEM_POSITION = 8;

    @Rule
    public ActivityTestRule<MoviesActivity> mActivityRule =
            new ActivityTestRule(MoviesActivity.class);

    @Test
    public void scrollToItemAtPositionAndCheckRatingBarVisibility() {
        // First, scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.recyclerViewMovies))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION,
                        click()));

        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnMenuItemRatingAndCheckVisibilityOfDialog() {
        onView(withId(R.id.imageViewSearch)).perform(ViewActions.click());
        onView(withText(R.string.menu_search_by_rating)).perform(click());
        onView(withText(R.string.menu_search_by_rating))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnMenuItemYearAndCheckVisibilityOfDialog() {
        onView(withId(R.id.imageViewSearch)).perform(ViewActions.click());
        onView(withText(R.string.menu_search_by_year)).perform(click());
        onView(withText(R.string.menu_search_by_year))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnSearchIconAndTestLabelsVisibility() {
        onView(withId(R.id.imageViewSearch)).perform(ViewActions.click());
        onView(withText(R.string.rating)).check(matches(isDisplayed()));
        onView(withText(R.string.year)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfTitleIsVisible() {
        onView(withText(R.string.title_movies)).check(matches(isDisplayed()));
    }

}
