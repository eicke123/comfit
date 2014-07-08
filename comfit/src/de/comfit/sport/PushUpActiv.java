package de.comfit.sport;

import android.content.Context;
import android.content.Intent;
import de.comfit.PushupActivity;

public class PushUpActiv extends SportActiv
{

   public PushUpActiv(Context context)
   {
      super(context);
      // TODO Auto-generated constructor stub
   }
   @Override
   public void start()
   {
      Intent intent = new Intent(context, PushupActivity.class);
      context.startActivity(intent);

   }

   @Override
   public void updateProgress(int percent)
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void complete()
   {
      // TODO Auto-generated method stub

   }

}
