package de.comfit;

import android.view.HapticFeedbackConstants;
import android.view.View;
import de.comfit.sport.SportActiv;

public class ClickSportActiv implements View.OnClickListener
{
   private static boolean active =true;
   private SportActiv sportActiv;
   

   public ClickSportActiv(SportActiv sportActiv)
   {
      this.sportActiv = sportActiv;
   }

   @Override
   public void onClick(View v)
   {
      if (isActive())
      {
         v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
         this.sportActiv.start(v);
      }
   }

   public static boolean isActive()
   {
      return active;
   }

   public static void setActive(boolean active)
   {
      ClickSportActiv.active = active;
   }
}