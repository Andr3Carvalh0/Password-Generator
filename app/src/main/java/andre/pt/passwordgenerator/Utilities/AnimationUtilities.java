package andre.pt.passwordgenerator.Utilities;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Supplier;
import java.util.LinkedList;
import java.util.List;

public class AnimationUtilities {

    public enum ANIMATION_TYPES{
        FADEIN(() -> {
            Animation animation = new AlphaAnimation(0, 1);
            animation.setInterpolator(new DecelerateInterpolator());
            return animation;
        }),

        FADEOUT(() -> {
            Animation animation = new AlphaAnimation(1, 0);
            animation.setInterpolator(new AccelerateInterpolator());
            return animation;
        });


        private Supplier<Animation> animation;

        ANIMATION_TYPES(Supplier<Animation> animation) {
            this.animation = animation;
        }

        public Supplier<Animation> getAnimation() {
            return animation;
        }
    }

    public static class Builder{

        private long animationDuration = 500;
        private ANIMATION_TYPES animationType = ANIMATION_TYPES.FADEIN;
        private List<Animation> animations;
        private Consumer<Animation> onAnimationStart;
        private Consumer<Animation> onAnimationEnd;
        private Consumer<Animation> onAnimationRepeat;

        public Builder setAnimationDuration(long time){
            animationDuration = time;
            return this;
        }

        public Builder setFadeInAnimation(){
            animationType = ANIMATION_TYPES.FADEIN;
            return this;
        }

        public Builder setFadeOutAnimation(){
            animationType = ANIMATION_TYPES.FADEOUT;
            return this;
        }

        public Builder addAnimation(){
            if(animations == null)
                animations = new LinkedList<>();

            animations.add(buildAnimation());
            return this;
        }

        public Builder addOnAnimationEndEvent(Consumer<Animation> event){
            this.onAnimationEnd = event;
            return this;
        }

        public Builder addOnAnimationStartEvent(Consumer<Animation> event){
            this.onAnimationStart = event;
            return this;
        }

        public Builder addOnAnimationRepeatEvent(Consumer<Animation> event){
            this.onAnimationRepeat = event;
            return this;
        }

        // Terminal Operations
        public Animation buildAnimation(){
            Animation animation = animationType.getAnimation().get();
            animation.setDuration(animationDuration);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if(onAnimationStart != null)
                        onAnimationStart.accept(animation);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(onAnimationEnd != null)
                        onAnimationEnd.accept(animation);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if(onAnimationRepeat != null)
                        onAnimationRepeat.accept(animation);
                }
            });

            return animation;
        }
    }

    public static AnimationUtilities.Builder create(){
        return new Builder();
    }

}
