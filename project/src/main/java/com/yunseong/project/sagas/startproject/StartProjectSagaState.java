package com.yunseong.project.sagas.startproject;

import com.yunseong.project.api.command.ApproveTeamCommand;
import com.yunseong.project.api.command.RejectTeamCommand;
import com.yunseong.project.sagaparticipants.RejectProjectCommand;
import com.yunseong.project.sagaparticipants.StartProjectCommand;
import com.yunseong.weclass.api.command.CreateWeClassCommand;
import com.yunseong.weclass.api.command.CreateWeClassReply;
import lombok.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Getter
@Setter
@RequiredArgsConstructor
public class StartProjectSagaState {

    @NonNull
    private Long projectId;
    private Long weClassId;

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public RejectProjectCommand makeRejectProjectCommand() {
        return new RejectProjectCommand(this.projectId);
    }

    public RejectTeamCommand makeRejectTeamCommand() {
        return new RejectTeamCommand(this.projectId);
    }

    public ApproveTeamCommand makeApproveTeamCommand() {
        return new ApproveTeamCommand(this.projectId);
    }

    public CreateWeClassCommand makeCreateWeClassCommand() {
        return new CreateWeClassCommand(this.projectId);
    }

    public StartProjectCommand makeStartProjectCommand() {
        return new StartProjectCommand(this.projectId);
    }

    public void handleCreateWeClassReply(CreateWeClassReply reply) {
        this.weClassId = reply.getWeClassId();
    }
}
