package in.gvatreya.communications.model.dto;

import in.gvatreya.communications.model.Channel;
import in.gvatreya.communications.utils.ValidationResponse;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object for the Channel model
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor @ToString
public class ChannelDto {

    private String uuid;
    private String name;
    private String configuration;

    public static ChannelDto fromModel(@NonNull final Channel channel) {
        return new ChannelDto.ChannelDtoBuilder()
                .uuid(channel.getUuid())
                .name(channel.getName())
                .configuration(channel.getConfiguration())
                .build();
    }

    public Channel toModel(){
        return Channel.builder()
                .uuid(this.uuid)
                .name(this.name)
                .configuration(this.configuration)
                .build();
    }

    /**
     * Helper method that validates the ChannelDto object
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validate() {
        final ValidationResponse response = new ValidationResponse();
        // Set true by default
        response.setValid(true);
        final List<String> problems = new ArrayList<>();

        if(StringUtils.isBlank(this.name) ) {
            response.setValid(false);
            problems.add("name is empty/blank");
        }
        if(StringUtils.isBlank(this.configuration) ) {
            response.setValid(false);
            problems.add("configuration is empty/blank");
        }
        //FIXME: Add Json validation using available library
        response.setProblems(problems);
        return response;
    }
}
