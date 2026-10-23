package model;

public class Candidate {
    private int candidateId;
    private String name;
    private String party;

    public Candidate(int candidateId, String name, String party) {
        this.candidateId = candidateId;
        this.name = name;
        this.party = party;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    @Override
    public String toString() {
        return name + " (" + party + ")";
    }
}
