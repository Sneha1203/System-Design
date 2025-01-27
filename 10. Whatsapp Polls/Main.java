import java.util.*;

class Poll {
    private String pollId;
    private String question;
    private List<String> options;
    private Date createdAt;


    public Poll(String id, String question, List<String> options) {
        this.pollId = id;
        this.question = question;
        this.options = options;
        this.createdAt = new Date();
    }


    public String getPollId() {
        return pollId;
    }


    public String getQuestion() {
        return question;
    }


    public void setQuestion(String question) {
        this.question = question;
    }


    public List<String> getOptions() {
        return options;
    }


    public void setOptions(List<String> options) {
        this.options = options;
    }
}


class Vote {
    private String pollId;
    private String userId;
    private String option;
    private Date timestamp;


    public Vote(String pollId, String userId, String option) {
        this.pollId = pollId;
        this.userId = userId;
        this.option = option;
        this.timestamp = new Date();
    }


    public String getPollId() {
        return pollId;
    }


    public String getUserId() {
        return userId;
    }


    public String getOption() {
        return option;
    }
}


class PollManager {
    private Map<String, Poll> polls;
    private Map<String, Map<String, Integer>> pollResults;
    private Map<String, Set<String>> userVotes;


    public PollManager() {
        polls = new HashMap<>();
        pollResults = new HashMap<>();
        userVotes = new HashMap<>();
    }


    public String createPoll(String question, List<String> options) {
        String pollId = String.valueOf(polls.size() + 1);
        Poll poll = new Poll(pollId, question, options);
        polls.put(pollId, poll);


        Map<String, Integer> result = new HashMap<>();
        for (String option : options) {
            result.put(option, 0);
        }
        pollResults.put(pollId, result);


        return pollId;
    }


    public String updatePoll(String pollId, String question, List<String> options) {
        Poll poll = polls.get(pollId);
        if (poll != null) {
            poll.setQuestion(question);
            poll.setOptions(options);


            Map<String, Integer> result = new HashMap<>();
            for (String option : options) {
                result.put(option, 0);
            }
            pollResults.put(pollId, result);


            return "Poll updated successfully.";
        }
        return "Poll not found.";
    }


    public String deletePoll(String pollId) {
        if (polls.containsKey(pollId)) {
            polls.remove(pollId);
            pollResults.remove(pollId);
            userVotes.remove(pollId);
            return "Poll deleted successfully.";
        }
        return "Poll not found.";
    }


    public String voteInPoll(String pollId, String userId, String option) {
        Poll poll = polls.get(pollId);
        if (poll != null) {
            userVotes.putIfAbsent(pollId, new HashSet<>());
            Set<String> userVoteSet = userVotes.get(pollId);


            if (userVoteSet.contains(userId)) {
                return "User has already voted.";
            }


            Map<String, Integer> result = pollResults.get(pollId);
            if (result.containsKey(option)) {
                result.put(option, result.get(option) + 1);
                userVoteSet.add(userId);
                return "Vote cast successfully.";
            } else {
                return "Invalid option.";
            }
        }
        return "Poll not found.";
    }


    public String viewPollResults(String pollId) {
        Map<String, Integer> results = pollResults.get(pollId);
        if (results != null) {
            StringBuilder resultString = new StringBuilder();
            for (Map.Entry<String, Integer> entry : results.entrySet()) {
                resultString.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            return resultString.toString();
        }
        return "Poll not found.";
    }
}


public class Main {
    public static void main(String[] args) {
        PollManager pollManager = new PollManager();


        // Creating a poll
        String pollId = pollManager.createPoll("What is your favorite color?", Arrays.asList("Red", "Blue", "Green", "Yellow"));
        System.out.println("Poll created with ID: " + pollId);


        // Voting in the poll
        System.out.println(pollManager.voteInPoll(pollId, "user1", "Red"));
        System.out.println(pollManager.voteInPoll(pollId, "user2", "Blue"));
        System.out.println(pollManager.voteInPoll(pollId, "user1", "Green")); // Should inform the user has already voted


        // Viewing poll results
        System.out.println("Poll results for poll ID " + pollId + ":\n" + pollManager.viewPollResults(pollId));


        // Updating the poll
        System.out.println(pollManager.updatePoll(pollId, "What is your favorite primary color?", Arrays.asList("Red", "Blue", "Yellow")));


        // Voting in the updated poll
        System.out.println(pollManager.voteInPoll(pollId, "user3", "Yellow"));


        // Viewing updated poll results
        System.out.println("Updated poll results for poll ID " + pollId + ":\n" + pollManager.viewPollResults(pollId));


        // Deleting the poll
        System.out.println(pollManager.deletePoll(pollId));


        // Attempting to view results of a deleted poll
        System.out.println(pollManager.viewPollResults(pollId));
    }
}
