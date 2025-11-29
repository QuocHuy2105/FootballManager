/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.service;

import N23DCCN058.fm.dao.MatchDAO;
import N23DCCN058.fm.dao.MatchEventDAO;
import N23DCCN058.fm.dao.MatchPlayerDAO;
import N23DCCN058.fm.dao.MatchTeamDAO;
import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.exception.ServiceException;
import N23DCCN058.fm.exception.ValidationException;
import N23DCCN058.fm.model.LeagueTableRow;
import N23DCCN058.fm.model.Player;
import N23DCCN058.fm.model.PlayerStatistic;
import N23DCCN058.fm.model.Referee;
import N23DCCN058.fm.model.RefereeStatistic;
import N23DCCN058.fm.model.TeamStatistic;
import N23DCCN058.fm.model.TeamStatisticInMatch;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author WokWee
 */
public class StatisticService {
    
    public TeamStatistic getTeamStatistic(int teamId){
        int numOfPlayers, numOfPlayedMatches, numOfWins, numOfLoses, numOfDraws, numOfGoals, rank, totalPoints;
        
        LeagueTableService lts = new LeagueTableService();
        LeagueTableRow teamStatistic = lts.calculateLeagueTable().stream()
                                                                .filter(x -> x.getTeamId() == teamId)
                                                                .findFirst()
                                                                .orElse(null);
        if(teamStatistic == null)
            throw new ValidationException("Không thể tìm thấy đội bóng!");
        
        PlayerService ps = new PlayerService();
        numOfPlayers = ps.countNumberOfPlayerInTeam(teamId);
        
        try {
            MatchTeamDAO dao = new MatchTeamDAO();
            numOfPlayedMatches = dao.countTotalPlayedMatchedOfTeam(teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể đếm số trận đã tham gia: " + e.getMessage(), e);
        }
        
        numOfWins = teamStatistic.getWins();
        numOfLoses = teamStatistic.getLosses();
        numOfDraws = teamStatistic.getDraws();
        numOfGoals = teamStatistic.getGoalsFor();
        rank = teamStatistic.getRank();
        totalPoints = teamStatistic.getPoints();
        
        return new TeamStatistic(numOfPlayers, numOfPlayedMatches, numOfWins, numOfLoses, numOfDraws, numOfGoals, rank, totalPoints);
    }
    
    public PlayerStatistic getPlayerStatistic(int playerId){
        int numOfPlayedMatches, numOfStartingRole, totalPlayedTimes, numOfGoals, numOfPenaltyGoals, numOfOwnGoals, numOfFouls, numOfYellowCards, numOfRedCards;
        
        try {
            MatchPlayerDAO dao = new MatchPlayerDAO();
            numOfPlayedMatches = dao.countTotalPlayedMatches(playerId);
            numOfStartingRole = dao.countTotalStartingPlayedMatches(playerId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể đếm số trận đã chơi của cầu thủ: " + e.getMessage(), e);
        }
        
        MatchEventService mts = new MatchEventService();
        totalPlayedTimes = mts.countTotalPlayedTimeOfPlayer(playerId);
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            numOfGoals = dao.countGoalsOfPlayer(playerId);
            numOfPenaltyGoals = dao.countPenaltyGoalsOfPlayer(playerId);
            numOfOwnGoals = dao.countOwnGoalsOfPlayer(playerId);
            numOfFouls = dao.countFoulsOfPlayer(playerId);
            numOfYellowCards = dao.countYellowCardsOfPlayer(playerId);
            numOfRedCards = dao.countRedCardsOfPlayer(playerId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy các thông số của cầu thủ: " + e.getMessage(), e);
        }
        
        return new PlayerStatistic(numOfPlayedMatches, numOfStartingRole, totalPlayedTimes, numOfGoals, numOfPenaltyGoals, numOfOwnGoals, numOfFouls, numOfYellowCards, numOfRedCards);
    }
    
    public RefereeStatistic getRefereeStatistic(int refereeId){
        int numOfMatchJoins, numOfFinishedMatches;
        
        try {
            MatchDAO dao = new MatchDAO();
            numOfMatchJoins = dao.countTotalMatchesOfReferee(refereeId);
            numOfFinishedMatches = dao.countTotalFinishedMatchesOfReferee(refereeId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy các thông số của trọng tài: " + e.getMessage(), e);
        }
        
        return new RefereeStatistic(numOfMatchJoins, numOfFinishedMatches);
    }
    
    public TeamStatisticInMatch getTeamStatisticInMatch(int matchId, int teamId){
        int totalGoals, totalPenaltyGoals, totalOwnGoals, totalFouls, totalYellowCards, totalRedCards;
        
        MatchEventService mes = new MatchEventService();
        
        totalGoals = mes.countGoalsOfTeamInMatch(matchId, teamId);
        totalPenaltyGoals = mes.countPenaltyGoalsOfTemInMatch(matchId, teamId);
        totalOwnGoals = mes.countOwnGoalsOfTeamInMatch(matchId, teamId);
        totalFouls = mes.countFoulsOfTeamInMatch(matchId, teamId);
        totalYellowCards = mes.countYellowCardsOfTeamInMatch(matchId, teamId);
        totalRedCards = mes.countRedCardsOfTeamInMatch(matchId, teamId);
        
        return new TeamStatisticInMatch(totalGoals, totalPenaltyGoals, totalOwnGoals, totalFouls, totalYellowCards, totalRedCards);
    }
    
    public Map<Player, Integer> calculatePlayerRanking(){
        
        try{
            PlayerService ps = new PlayerService();
            MatchEventDAO dao = new MatchEventDAO();
            Map<Player, Integer> playerGoals = ps.getAllPlayer().stream()
                        .collect(Collectors.toMap(
                                Player -> Player,
                                Player -> dao.countGoalsOfPlayer(Player.getPlayerId())
                         ));

            return playerGoals.entrySet().stream()
                        .sorted((x, y) -> {
                            if(!Objects.equals(x.getValue(), y.getValue())) return y.getValue() - x.getValue();
                            else return Integer.compare(x.getKey().getPlayerId(), y.getKey().getPlayerId());
                        })
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue,
                                LinkedHashMap::new
                        ));
            
        } catch (DatabaseException e){
            throw new ServiceException("Không thể lấy bảng xếp hạng cầu thủ: " + e.getMessage() ,e);
        }
        
    }
    
    public Map<Referee, RefereeStatistic> calculateRefereeRanking(){
        
        try {
            RefereeService rs = new RefereeService();
            MatchDAO md = new MatchDAO();
            
            Map<Referee, RefereeStatistic> refereeMatchesFinished = rs.getAllReferee().stream()
                        .collect(Collectors.toMap(
                                Referee -> Referee,
                                Referee -> getRefereeStatistic(Referee.getRefereeId())
                        ));
            return refereeMatchesFinished.entrySet().stream()
                    .sorted((x, y) -> {
                        if(!Objects.equals(x.getValue().getNumOfFinishedMatches(), y.getValue().getNumOfFinishedMatches()))
                            return y.getValue().getNumOfFinishedMatches() - x.getValue().getNumOfFinishedMatches();
                        else if(!Objects.equals(x.getValue().getNumOfMatchesJoin(), y.getValue().getNumOfMatchesJoin()))
                            return y.getValue().getNumOfMatchesJoin() - x.getValue().getNumOfMatchesJoin();
                        else return Integer.compare(x.getKey().getRefereeId(), y.getKey().getRefereeId());
                    })
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue,
                            LinkedHashMap::new
                    ));
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy bảng xếp hạng trọng tài: " + e.getMessage() ,e);
        }
    }
    
}
