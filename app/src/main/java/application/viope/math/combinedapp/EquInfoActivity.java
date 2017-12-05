package application.viope.math.combinedapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class EquInfoActivity extends AppCompatActivity {

    private ListView listView;
    final String[] listItemArray = {
            "Symbol meanings",    //0
            "Multiplication",   //1
            "Variable",   //2
            "Division",   //3
            "Add parentheses",  //4
            "Move cursor to the left",   //5
            "Move cursor to the right", //6
            "Space",   //7
            "Correct final answer",    //8
            "Incorrect answer",   //9
            "Correct answer",     //10
            "Exercise list colour meanings",   //11
            "Blue: Not yet tried", //12
            "Yellow: Incomplete", //13
            "Green: Done", // 14
            "Instructions", //15
            "1. The goal of every exercise is to solve the variable X",
            "2. Solve the exercises phase by phase and input them into the app as you go",
            "3. Inputting the final answer right away is possible, but solving in phases is encouraged",
            "4. Always solve from left to right",
            "5. Parentheses -> Division/multiplication -> Addition/subtraction",
            "6. Remember to multiply numbers with following parentheses' contents, e.g. 5(3 - 2) = 5 * 1"
    };

    final int[] symbolList = {
            R.drawable.equ_asterisk, //0
            R.drawable.equ_x, //1
            R.drawable.equ_divide, //2
            R.drawable.equ_brackets, //3
            R.drawable.equ_triangle_left, //4
            R.drawable.equ_triangle_right, //5
            R.drawable.equ_space, //6
            R.drawable.equ_correct, //7
            R.drawable.equ_incorrect, //8
            R.drawable.equ_thumb //9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equ_activity_info);
        listView = (ListView) findViewById(R.id.infoView);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.equ_toolbar_custom2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        InfoAdapter infoAdapter = new InfoAdapter(this, R.id.text, listItemArray);
        listView.setAdapter(infoAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public class ViewHolder {
        TextView text;

        public ViewHolder(TextView text) {
            this.text = text;
        }

        public TextView getText() {
            return text;
        }

        public void setText(TextView text) {
            this.text = text;
        }

    }

    public class InfoAdapter extends ArrayAdapter {

        String[] infoArray = listItemArray;

        @Override
        public int getViewTypeCount() {
            return 3;
        }


        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position == 11) {
                return 0;
            } else if (position > 0 && position < 11) {
                return 1;
            } else {
                return 2;
            }
        }


        public InfoAdapter(Context context, int resource, String[] listItemArray) {
            super(context, resource, listItemArray);
            this.infoArray = listItemArray;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            String infoItem = listItemArray[position];

            if (convertView == null) {
                if (position == 0 || position == 11 || position == 15) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.equ_activity_info_titlerow, null, false);
                } else if (position > 0 && position < 11) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.equ_activity_info_contentrow_symbol, null, false);
                    ImageView imageView = (ImageView) convertView. findViewById(R.id.symbol);
                    imageView.setImageResource(symbolList[position - 1]);
                } else {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.equ_activity_info_contentrow, null, false);
                }

                TextView textView = (TextView) convertView.findViewById(R.id.text);
                viewHolder = new ViewHolder(textView);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.getText().setText(infoItem);

            return convertView;
        }
    }

    public void backToolbarClicked(View view) {
        finish();
    }
}
