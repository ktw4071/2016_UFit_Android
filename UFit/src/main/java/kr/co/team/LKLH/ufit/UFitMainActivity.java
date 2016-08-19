package kr.co.team.LKLH.ufit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;


public class UFitMainActivity extends AppCompatActivity implements MemberItemAdapter.DeleteMemberCallback {


    DrawerLayout drawlayout;
    Toolbar toolbar;
    ImageView toolbarLeft, toolbarRight;
    ImageView manageSchedule;
    TextView toolbarHead, calendarDate, leftHeadName;
    CircleImageView leftHeadImg;
    EditText rightDrawMemberSearch;
    Intent intent;
    Calendar calendar = Calendar.getInstance();
    int currentPosition;
    int this_year = calendar.get(Calendar.YEAR);
    int this_month= calendar.get(Calendar.MONTH);
    int this_today= calendar.get(Calendar.DATE);
    int this_maxday=calendar.getActualMaximum(calendar.DAY_OF_MONTH);
    boolean member_deleted;
    JSONObject jsonObject;

    @Override
    public void deleteMethodCallBack(final ArrayList<UFitEntityObject> list, final UFitEntityObject object, final MemberItemAdapter adapter) {
        AlertDialog dialog = null;
        DialogInterface.OnClickListener dialog_schedule_member_delete = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new MainDeleteMemberSchedule().execute(object._sid);
                list.remove(object);
                adapter.notifyDataSetChanged();
            }
        };
        DialogInterface.OnClickListener dialog_schedule_member_cancel = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        dialog = new AlertDialog.Builder(this)
                .setTitle("스케쥴 삭제")
                .setPositiveButton("삭제", dialog_schedule_member_delete)
                .setNegativeButton("취소", dialog_schedule_member_cancel)
                .setCancelable(true).create();

        dialog.show();

        Log.i("leelog", member_deleted + "");
    }


    public class MainDeleteMemberSchedule extends AsyncTask<Integer, Integer, Integer>{
        @Override
        protected Integer doInBackground(Integer... integers) {
            UFitHttpConnectionHandler.deleteMemberSchedule(integers[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufit_activity_main);
        (new AsyncMainTrainerData()).execute();
        jsonObject = new JSONObject();
        // 드로우레이아웃
        drawlayout = (DrawerLayout)findViewById(R.id.uf_left_drawer);
        rightDrawMemberSearch = (EditText)findViewById(R.id.rightdrawsearch);

        manageSchedule = (ImageView)findViewById(R.id.uf_schedule_fab) ;
        //툴바 설정
        toolbar = (Toolbar) findViewById(R.id.uf_main_toolbar);
        setSupportActionBar(toolbar);
        toolbarHead = (TextView)findViewById(R.id.uf_toolbar_head);
        toolbarLeft = (ImageView)findViewById(R.id.uf_toolbar_left);
        toolbarRight= (ImageView)findViewById(R.id.uf_toolbar_right);
        toolbarLeft.setImageResource(R.drawable.btn_menu);
        toolbarRight.setImageResource(R.drawable.btn_menu2);
        setupUIforRightDraw(findViewById(R.id.uf_left_drawer));

        // Right Draw, 트레이너의 회원 목록 생성
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.uf_right_menu, UFitRightDrawRCV.newInstance())
                .commit();
        leftHeadImg = (CircleImageView)findViewById(R.id.uf_left_headimg);
        leftHeadName= (TextView)findViewById(R.id.uf_left_headtext);

        // 뷰페이저 설정
        final ViewPager viewPager = (ViewPager)findViewById(R.id.uf_schedule_viewpager);
        UFitMainFragmentPagerAdapter adapter = new UFitMainFragmentPagerAdapter(getSupportFragmentManager(), this_year, this_month, this_today);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(this_today + 25000);
        viewPager.getCurrentItem();
        // 뷰페이저의 날짜 설정 및 페이지 체인지리스너
        currentPosition = viewPager.getCurrentItem();
        calendarDate = (TextView)findViewById(R.id.uf_main_date);
        calendarDate.setText(this_year + "." + (this_month+1) + "." + this_today);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {

                if (currentPosition < position) {
                    if(this_today == this_maxday) {
                        this_today = 1;
                        this_month++;
                    } else {
                        ++this_today;
                    }
                } else if(currentPosition > position){
                    if(this_today == 1) {
                        this_today = this_maxday;
                        this_month--;
                    } else {
                        --this_today;
                    }
                }
                currentPosition = position;
                calendarDate.setText(this_year + ". " + String.format("%02d", (this_month+1)) + ". " + String.format("%02d", this_today));
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }


    @Override
    protected void onResume() {
        super.onResume();


        // 툴바 드로우 레이아웃 버튼
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawlayout.openDrawer(GravityCompat.START);
            }
        });
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawlayout.openDrawer(GravityCompat.END);
            }
        });
        //*트레이너 메뉴 프로필
        final LinearLayout menuTrProfile = (LinearLayout) findViewById(R.id.uf_left_menu_profil);
        menuTrProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(UFitMainActivity.this, UFitTranerProfileActivity.class);
                startActivity(intent);
                drawlayout.closeDrawer(GravityCompat.START);
            }
        });

        /*// 메인 트레이너 프로필 이미지 확대 및 삭제
        leftHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UFitMainActivity.this, new_UFitImageViewer.class);
                intent.putExtra("data", jsonObject.toString());
                intent.putExtra("url", UFitNetworkConstantDefinition.URL_UFIT_TRAINER_IMAGE_UPLOAD);
                startActivity(intent);
            }
        });*/
        //트레이너 메뉴 스케줄 관리
        final LinearLayout menuTrManageSchedule = (LinearLayout)findViewById(R.id.uf_left_menu_schedule);
        menuTrManageSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(UFitMainActivity.this, UFitManageSchedule.class);
                startActivity(intent);
                drawlayout.closeDrawer(GravityCompat.START);
            }
        });
        //트레이너 메뉴 고객관리
        final LinearLayout menuMemManage = (LinearLayout) findViewById(R.id.uf_left_menu_mem);
        menuMemManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(UFitMainActivity.this, UFitMemberManageActivity.class);
                startActivity(intent);
                drawlayout.closeDrawer(GravityCompat.START);
            }
        });
        //트레이너 메뉴 메세지
        final LinearLayout menuMessage = (LinearLayout)findViewById(R.id.uf_left_menu_message);
        menuMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(UFitMainActivity.this, UFitMessageActivity.class);
                startActivity(intent);
                drawlayout.closeDrawer(GravityCompat.START);
            }
        });
        // 트레이너 메뉴 세팅
        final LinearLayout menuSetting = (LinearLayout)findViewById(R.id.uf_left_menu_setting);
        menuSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(UFitMainActivity.this, UFitSettingActivity.class);
                startActivity(intent);
                drawlayout.closeDrawer(GravityCompat.START);
            }
        });
        // 플로팅 액션 버튼 -> 스케쥴 관리
        manageSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(UFitMainActivity.this, UFitManageSchedule.class);
                startActivity(intent);
            }
        });

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void setupUIforRightDraw(View view){
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideSoftKeyboard(UFitMainActivity.this);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0 ; i < ((ViewGroup)view).getChildCount() ; i++) {
                View innerView = ((ViewGroup)view).getChildAt(i);
                setupUIforRightDraw(innerView);
            }
        }
    }

    private class AsyncMainTrainerData extends AsyncTask<Void, Void, ArrayList<UFitEntityObject>> {
        @Override
        protected ArrayList<UFitEntityObject> doInBackground(Void... voids) {
            return new LosDatosDeLaRed_JSON().LosDatosDeLaRed_GET_JSON
                    (UFitNetworkConstantDefinition.URL_UFIT_TRAINER_SIMPLE_PROFILE, "data" , 6);
        }

        @Override
        protected void onPostExecute(ArrayList<UFitEntityObject> arrayList) {
            super.onPostExecute(arrayList);
            try {
                jsonObject.put("_image", arrayList.get(0)._image);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            toolbarHead.setText(arrayList.get(0)._name);
            leftHeadName.setText(arrayList.get(0)._name);
            if (arrayList.get(0)._thumbnail != null) {
                Glide.with(UFitApplication.getUFitContext()).load(arrayList.get(0)._image).into(leftHeadImg);
            } else {
                leftHeadImg.setImageResource(R.drawable.avatar_m);
            }

        }
    }
    @Override
    public void onBackPressed() {
        if(drawlayout.isDrawerOpen(GravityCompat.START) || drawlayout.isDrawerOpen(GravityCompat.END)) {
            drawlayout.closeDrawers();
        } else {
            super.onBackPressed();
        }

    }
}


