package com.nohnari.missingchild.domain.child.domain;

import com.nohnari.missingchild.domain.child.entity.MissingChild;
import com.nohnari.missingchild.domain.user.entity.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

class MissingChildTest {

    @Nested
    class Constructor {

        @Nested
        class Success {

            @Test
            void MissingChild_객체_생성() {
                // given
                String childName = "John Doe";
                Character childGender = 'M';
                LocalDate dateOfBirth = LocalDate.of(2010, 1, 1);
                Integer childAge = 13;
                String lastKnownLocation = "Central Park";
                LocalDate missingSince = LocalDate.of(2023, 11, 1);
                String photoUrl = "https://example.com/photo.jpg";

                User user = Mockito.mock(User.class);

                // when
                MissingChild missingChild = new MissingChild();
                missingChild.setChildName(childName);
                missingChild.setChildGender(childGender);
                missingChild.setDateOfBirth(dateOfBirth);
                missingChild.setChildAge(childAge);
                missingChild.setLastKnownLocation(lastKnownLocation);
                missingChild.setMissingSince(missingSince);
                missingChild.setPhotoUrl(photoUrl);
                missingChild.setUser(user);

                // then
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(missingChild).isNotNull();
                    softly.assertThat(missingChild.getChildName()).isEqualTo(childName);
                    softly.assertThat(missingChild.getChildGender()).isEqualTo(childGender);
                    softly.assertThat(missingChild.getDateOfBirth()).isEqualTo(dateOfBirth);
                    softly.assertThat(missingChild.getChildAge()).isEqualTo(childAge);
                    softly.assertThat(missingChild.getLastKnownLocation()).isEqualTo(lastKnownLocation);
                    softly.assertThat(missingChild.getMissingSince()).isEqualTo(missingSince);
                    softly.assertThat(missingChild.getPhotoUrl()).isEqualTo(photoUrl);
                    softly.assertThat(missingChild.getUser()).isNotNull();
                    softly.assertThat(missingChild.getCreatedAt()).isNotNull();
                    softly.assertThat(missingChild.getUpdatedAt()).isNotNull();
                });
            }
        }
    }
}