package com.lq.ren.unit920;



import android.util.Log;

import com.lq.ren.many.calendar.compare170222.CompareUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Author lqren on 17/2/22.
 */
@PrepareForTest({CompareUtil.class})
public class CompareUtilTest {

    private static final CompareNameBuilder[] COMPARE_NAMES = {
            new CompareNameBuilder("NaN", "", 0), // empty version & invalid version is same.
            new CompareNameBuilder("Hemu_1.0.0", "", 1), // empty version is small.
            new CompareNameBuilder("Hemu_1.0.1", "Hemu_1.0", 1),  // invalid version is small.
            new CompareNameBuilder("Hemu_1.0.0", "hemu_1.0.0.0", 0),  // sub version
            new CompareNameBuilder("Hemu_1.0.0.2", "hemu_1.0.1", 1),  // sub version compare
            new CompareNameBuilder("Hemu_1.0.1.0", "hemu_1.1.0", 1),  // main version compare first.
            new CompareNameBuilder("Hemu_1.0.1.0", "1.0.1", 0),      // no prefix
            new CompareNameBuilder("Hemu_1.0.1", "Hemu_1.0.1_a1", 0), // not support alpha version.
            new CompareNameBuilder("Hemu_1.0.1", "Hemu_1.0.1_b1", 0), // not support beta version.
            new CompareNameBuilder("Hemu_1.0.1", "Hemu_1.0.1_intl", 0), // intl version compare.
            new CompareNameBuilder("Hemu_1.0.1", "Hemu_1.0.1", 0),    // capital version name.
    };

    @Test
    public void test_compare_name() {
        for (CompareNameBuilder nameBuilder : COMPARE_NAMES) {
            String result = String.format(Locale.US, "%s vs %s != %d",
                    nameBuilder.oldName, nameBuilder.newName, nameBuilder.result);
            assertThat(result, CompareUtil.compareName(nameBuilder.oldName, nameBuilder.newName),
                        equalTo(nameBuilder.result));
        }
    }

    static class CompareNameBuilder{

        private String oldName;
        private String newName;
        private int result;

        public CompareNameBuilder(String s1, String s2, int r) {
            oldName = s1;
            newName = s2;
            result = r;
        }

    }
}
