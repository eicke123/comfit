package de.comfit;

import android.view.HapticFeedbackConstants;
import android.view.View;
import de.comfit.sport.SportActiv;

public class ClickSportActiv implements View.OnClickListener
{
   public static boolean active;
   private SportActiv sportActiv;
   
   public static boolean acvive = true;

   public ClickSportActiv(SportActiv sportActiv)
   {
      this.sportActiv = sportActiv;
   }

   @Override
   public void onClick(View v)
   {
<<<<<<< 5bd3435bd76c0a96070e5c9d70389e8d5003ee5b
      if (active)
=======
      if (acvive)
>>>>>>> 1a808a5fd522e748530e135018558138198017b9
      {
         v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
         this.sportActiv.start(v);
      }
   }
}