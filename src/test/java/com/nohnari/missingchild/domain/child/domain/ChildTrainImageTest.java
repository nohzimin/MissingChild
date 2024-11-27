package com.nohnari.missingchild.domain.child.domain;

import com.nohnari.missingchild.domain.child.entity.ChildTrainImage;
import com.nohnari.missingchild.domain.child.entity.MissingChild;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ChildTrainImageTest {

    @Nested
    class Constructor {

        @Nested
        class Success {

            @Test
            void ChildTrainImage_객체_생성() {
                // given
                String imageUrl = "https://example.com/image.jpg";
                MissingChild missingChild = Mockito.mock(MissingChild.class);

                // when
                ChildTrainImage childTrainImage = new ChildTrainImage();
                childTrainImage.setImageUrl(imageUrl);
                childTrainImage.setMissingChild(missingChild);

                // then
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(childTrainImage).isNotNull();
                    softly.assertThat(childTrainImage.getImageUrl()).isEqualTo(imageUrl);
                    softly.assertThat(childTrainImage.getMissingChild()).isNotNull();
                });
            }
        }
    }
}