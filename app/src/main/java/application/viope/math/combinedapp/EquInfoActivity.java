package application.viope.math.combinedapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class EquInfoActivity extends Activity {

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
            "Exercise colour meanings",   //11
            "Blue: Not yet tried", //12
            "Yellow: Incomplete", //13
            "Green: Done", // 14
            "Tips", //15
            "- The goal of every exercise is to solve the variable X",
            "- Always solve from left to right",
            "- Parentheses -> Division/multiplication -> Addition/subtraction",
            "- Remember to multiple numbers with following parentheses' contents, e.g. 5(3 - 2) = 5 * 1"
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
        listView = findViewById(R.id.infoView);

        InfoAdapter infoAdapter = new InfoAdapter(this, R.id.text, listItemArray);
        listView.setAdapter(infoAdapter);
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
                    ImageView imageView = convertView. findViewById(R.id.symbol);
                    imageView.setImageResource(symbolList[position - 1]);
                } else {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.equ_activity_info_contentrow, null, false);
                }

                TextView textView = convertView.findViewById(R.id.text);
                viewHolder = new ViewHolder(textView);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.getText().setText(infoItem);

            return convertView;
        }
    }

}
