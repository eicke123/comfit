package de.comfit;

import android.view.HapticFeedbackConstants;
import android.view.View;
import de.comfit.sport.SportActiv;

public class ClickSportActiv implements View.OnClickListener
{
   public static boolean active;
   private SportActiv sportActiv;

   public ClickSportActiv(SportActiv sportActiv)
   {
      this.sportActiv = sportActiv;
   }

   @Override
   public void onClick(View v)
   {
      if (active)
      {
         v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
         this.sportActiv.start(v);
      }
   }
}