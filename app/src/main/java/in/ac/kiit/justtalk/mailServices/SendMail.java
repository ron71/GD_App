package in.ac.kiit.justtalk.mailServices;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import in.ac.kiit.justtalk.models.AppUser;
import in.ac.kiit.justtalk.models.GDEvent;
import in.ac.kiit.justtalk.models.Scores;


/**
 * Created by Rohan on 1/4/2019.
 */

//Class is extending AsyncTask because this class is going to perform a networking operation
public class SendMail extends AsyncTask<Void,Void,Void> {

    //Declaring Variables
    private Context context;
    private Session session;

    //Information to send email
    GDEvent event;
    String orgName;

    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;


    private void addCondutedEvent(){

        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        String id = u.getEmail().substring(0,u.getEmail().indexOf("@"));

        final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            AppUser user = null;
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document!=null){
                        Log.e("Document", document.toString());
                        user= task.getResult().toObject(AppUser.class);
                        if(user!=null){
                            user.getVentsConducted().add(0,event.getGdID());
                            documentReference.set(user);
                        }else{
                            Log.e("NO User","NULL");
                        }
                    }
                }
            }
        });
    }






    private String getMessage(String id,String name,Scores score)
    {int f,l,b,c,t;
        f=score.getFluency();
        l=score.getLanguage();
        b=score.getBodyLanguage();
        c=score.getContent();
        t=score.getTeamWork();

        String fluency="",language="",bodylang="",content="",teamwork="";

        if(f==5){
            fluency="Rajdhani Express, you speak without any MTI";
        }
        else if(f==4){
            fluency="You speak breezily but halt at times, and minimum MTI";
        }
        else if(f==3){
            fluency="You speak fluently, but with MTI and pauses";
        }
        else if(f==2){
            fluency="Give many pauses, you love to retain accent";
        }
        else if(f==1){
            fluency="Hunting for words, you speak in local flavour";
        }

        if(l==5){
            language="Shakespeare and Shashi Tharoor are proud of you";
        }
        else if(l==4){
            language="Shakespeare still loves you, but others found faults";
        }
        else if(l==3){
            language="Language is still English, but Facts > Parts of Speech";
        }
        else if(l==2){
            language="Everyone understands, but struggle to";
        }
        else if(l==1){
            language="Grammar has retired. Vocab surrendered";
        }

        if(b==5){
            bodylang="You move your hands gracefully";
        }
        else if(b==4){
            bodylang="Smiling face reflects the feelings of heart";
        }
        else if(b==3){
            bodylang="You sit straight and lean forward in the chair";
        }
        else if(b==2){
            bodylang="You make eye contact with ALL";
        }
        else if(b==1){
            bodylang="Eyes up, hands tied, lost in interstellar";
        }

        if(c==5){
            content="You speak on both sides, giving many facts";
        }
        else if(c==4){
            content="You speak for and against, giving some facts and figures and correcting people";
        }
        else if(c==3){
            content="You speak well on one topic giving few facts and figures";
        }
        else if(c==2){
            content="You speak on borrowed points, no facts";
        }
        else if(c==1){
            content="You don't contribute at all, just nod";
        }

        if(t==5){
            teamwork="You suppress chaos, motivate quiet and track time \n"+
                    "\t\t You listen sincerely, appreciate and support points \n" +
                    "\t\t You initiate the talk, keep it on track and sum up ";
        }
        else if(t==4){
            teamwork="You defuse deadlocks, facilitate talk-turns and seek clarifications \n"+
                    "\t\t You support and oppose points equally using diplomatic language \n"+
                    "\t\t You start well and summarize all points on both sides";
        }
        else if(t==3){
            teamwork="You speak in turn, gives chance to silent and follow the 'leader' \n"+
                    "\t\t You don't interrupt, listen well and agree to the majority \n"+
                    "\t\t You speak at the start and keep the discussion on track";
        }
        else if(t==2){
            teamwork="You use more time than allotted and manage the group \n"+
                    "\t\t You cut into other's talk, disagree and you are blunt \n"+
                    "\t\t You speak in first half of the discussion";
        }
        else if(t==1){
            teamwork="You dominate the group and prioritize the topic over people\n"+
                    "\t\t You interrupt often, question more and get too aggressive\n"+
                    "\t\t You contribute towards the end of the discussion";
        }


        String msg="REPORT CARD OF YOUR SESSION \n\n*******************************************************************\n"+
                "ORGANISED BY : "+ name +"\n"+
                "SESSION ID : "+ id +"\n*******************************************************************\n\n"+
                "YOUR SCORE : \n\n" +
                "Fluency : "+f+"\n"+
                "\t\t"+fluency+"\n"+
                "Language : "+l+"\n"+
                "\t\t"+language+"\n"+
                "Body Language : "+b+"\n"+
                "\t\t"+bodylang+"\n"+
                "Content : "+c+"\n"+
                "\t\t"+content+"\n"+
                "Team Work : "+t+"\n"+
                "\t\t"+teamwork+"\n\n"+
                "Total Score : "+(f+l+b+c+t)+"/25";
        return msg;
    }


    //Class Constructor
    public SendMail(Context context, GDEvent event, String orgName){
        //Initializing variables
        this.context = context;
        this.event = event;
        this.orgName = orgName;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Sending reports","Please wait...",false,false);

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message
        Toast.makeText(context,"Message Sent", Toast.LENGTH_LONG).show();
        ((Activity)context).finish();
    }


    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                    }
                });

      for(int i=0; i<event.getPlayerIDs().size();i++){
          try {

              final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(event.getPlayerIDs().get(i).getId());
              documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                  AppUser user = null;
                  @Override
                  public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                      if(task.isSuccessful()){
                          DocumentSnapshot document = task.getResult();
                          if(document!=null){
                              Log.e("Document", document.toString());
                              user= task.getResult().toObject(AppUser.class);
                              if(user!=null){
                                  user.getVents().add(0,event.getGdID());
                                  documentReference.set(user);
                              }else{
                                  Log.e("NO User","NULL");
                              }
                          }
                      }
                  }
              });

              //Creating MimeMessage object
              MimeMessage mm = new MimeMessage(session);

              //Setting sender address
              mm.setFrom(new InternetAddress(Config.EMAIL));
              //Adding receiver
              mm.addRecipient(Message.RecipientType.TO, new InternetAddress(event.getPlayerIDs().get(i).getId()+"@kiit.ac.in"));
              //Adding subject
              mm.setSubject("GD CLUB: EVALUATED REPORT");
              //Adding message
              mm.setText(getMessage(event.getGdID(),orgName, event.getPlayerIDs().get(i)));

              //Sending email
              Transport.send(mm);

          } catch (MessagingException e) {
              e.printStackTrace();
          }
    }   addCondutedEvent();

        return null;
    }
}

