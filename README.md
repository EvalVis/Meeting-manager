## Test cases
Test case -> expected result
* Creating meeting -> a list size returned from json is incremented by 1.
* Creating meeting -> list is not null before and after creation.
<br/>

* Person who is not responsible for the meeting tries to delete it -> list still contains meeting.
* Responsible person tries to delete meeting -> meeting is no longer in the list.
<br/>

* Trying to add person to the meeting the first time -> Person is added to the meeting, no warning issued.
* Trying to add person to the meeting the second time -> Person is in the meeting, but list size does not change.
* Trying to add person to the meeting, however that person should participate in another meeting at that time -> person is added to the meeting, warning issued.
<br/>

* Trying to delete responsible person from the meeting -> person is still in the meeting.
* Trying to delete person who is not responsible for the meeting -> person is removed from the meeting.
<br/>

* Searching for meetings, there are no filters - all meetings are returned
* Other search cases.
<br/>
