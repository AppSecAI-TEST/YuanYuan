/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package xyz.zimuju.sample.widget;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import xyz.zimuju.sample.entity.User;

public class UserViewLayout extends BaseViewLayout<User> {

	public UserViewLayout(Context context) {
		super(context);
	}
	public UserViewLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public UserViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void init(Activity context) {
		super.init(context);
		createView(new UserView(context, getResources()));
	}

}
